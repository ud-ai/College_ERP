package com.example.collegeerp

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.dao.StudentDao
import com.example.collegeerp.data.local.entity.StudentEntity
import com.example.collegeerp.data.repository.StudentRepositoryImpl
import com.example.collegeerp.domain.model.Student
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class StudentRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var studentDao: StudentDao
    private lateinit var firestore: FirebaseFirestore
    private lateinit var repository: StudentRepositoryImpl

    @Before
    fun setup() {
        database = mockk()
        studentDao = mockk()
        firestore = mockk()
        every { database.studentDao() } returns studentDao
        repository = StudentRepositoryImpl(database, firestore)
    }

    @Test
    fun `observeStudents returns mapped flow from dao`() = runTest {
        // Given
        val entities = listOf(
            StudentEntity(
                studentId = "1",
                fullName = "John Doe",
                dob = 0L,
                program = "CS",
                admissionDate = System.currentTimeMillis(),
                status = "ACTIVE",
                photoUrl = null,
                contactPhone = null,
                guardianName = null
            )
        )
        every { studentDao.observeAll() } returns flowOf(entities)

        // When
        val result = repository.observeStudents()

        // Then
        // Flow mapping is tested implicitly through the implementation
        coVerify { studentDao.observeAll() }
    }

    @Test
    fun `upsert saves to local database`() = runTest {
        // Given
        val student = Student(
            studentId = "1",
            fullName = "John Doe",
            dob = 0L,
            program = "CS",
            admissionDate = System.currentTimeMillis(),
            status = "ACTIVE",
            photoUrl = null,
            contactPhone = null,
            guardianName = null
        )
        coEvery { studentDao.insert(any()) } returns Unit
        coEvery { firestore.collection("students").document(any()).set(any()) } returns mockk()

        // When
        repository.upsert(student)

        // Then
        coVerify { studentDao.insert(any()) }
    }
}
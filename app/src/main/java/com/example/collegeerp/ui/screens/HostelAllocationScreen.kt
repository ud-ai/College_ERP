package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Student(val id: String, val name: String)
data class HostelRoom(val hostelName: String, val roomNumber: String, val id: String)
data class OccupiedRoom(val student: Student, val room: HostelRoom, val allocationDate: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostelAllocationScreen(
    onBack: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStudent by remember { mutableStateOf<Student?>(null) }
    var selectedHostel by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("") }
    var allocationDate by remember { mutableStateOf("") }
    var selectedOccupiedRoom by remember { mutableStateOf<OccupiedRoom?>(null) }
    
    // Sample data
    val students = remember {
        listOf(
            Student("S001", "John Doe"),
            Student("S002", "Jane Smith"),
            Student("S003", "Mike Johnson"),
            Student("S004", "Sarah Wilson")
        )
    }
    
    val hostels = remember { listOf("Boys Hostel", "Girls Hostel") }
    val rooms = remember { 
        mapOf(
            "Boys Hostel" to listOf("Room 101", "Room 102", "Room 103"),
            "Girls Hostel" to listOf("Room 201", "Room 202", "Room 203")
        )
    }
    
    val occupiedRooms = remember {
        listOf(
            OccupiedRoom(
                Student("S001", "John Doe"),
                HostelRoom("Boys Hostel", "Room 101", "BH101"),
                "2024-01-15"
            ),
            OccupiedRoom(
                Student("S002", "Jane Smith"),
                HostelRoom("Girls Hostel", "Room 201", "GH201"),
                "2024-01-20"
            )
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF333333)
                )
            }
            Text(
                text = "Hostel Management",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Allocate / Deallocate",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search for student or room", color = Color(0xFF999999)) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF999999)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                )
            )
            
            // Allocate Room Section
            Text(
                text = "Allocate Room",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Student Dropdown
            var studentExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = studentExpanded,
                onExpandedChange = { studentExpanded = !studentExpanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = selectedStudent?.name ?: "Select Student",
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = studentExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color(0xFFE0E0E0)
                    )
                )
                ExposedDropdownMenu(
                    expanded = studentExpanded,
                    onDismissRequest = { studentExpanded = false }
                ) {
                    students.forEach { student ->
                        DropdownMenuItem(
                            text = { Text(student.name) },
                            onClick = {
                                selectedStudent = student
                                studentExpanded = false
                            }
                        )
                    }
                }
            }
            
            // Hostel and Room Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Hostel Dropdown
                var hostelExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = hostelExpanded,
                    onExpandedChange = { hostelExpanded = !hostelExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedHostel.ifEmpty { "Hostel" },
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = hostelExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2196F3),
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = hostelExpanded,
                        onDismissRequest = { hostelExpanded = false }
                    ) {
                        hostels.forEach { hostel ->
                            DropdownMenuItem(
                                text = { Text(hostel) },
                                onClick = {
                                    selectedHostel = hostel
                                    selectedRoom = "" // Reset room selection
                                    hostelExpanded = false
                                }
                            )
                        }
                    }
                }
                
                // Room Dropdown
                var roomExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = roomExpanded,
                    onExpandedChange = { roomExpanded = !roomExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedRoom.ifEmpty { "Room" },
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = roomExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2196F3),
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        ),
                        enabled = selectedHostel.isNotEmpty()
                    )
                    ExposedDropdownMenu(
                        expanded = roomExpanded,
                        onDismissRequest = { roomExpanded = false }
                    ) {
                        rooms[selectedHostel]?.forEach { room ->
                            DropdownMenuItem(
                                text = { Text(room) },
                                onClick = {
                                    selectedRoom = room
                                    roomExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            
            // Date Field
            OutlinedTextField(
                value = allocationDate,
                onValueChange = { allocationDate = it },
                placeholder = { Text("mm/dd/yyyy", color = Color(0xFF999999)) },
                trailingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Select Date",
                        tint = Color(0xFF999999)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                )
            )
            
            // Allocate Button
            Button(
                onClick = {
                    // Handle allocation logic
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                enabled = selectedStudent != null && selectedHostel.isNotEmpty() && 
                         selectedRoom.isNotEmpty() && allocationDate.isNotEmpty()
            ) {
                Text(
                    text = "Allocate",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
            
            // Deallocate Room Section
            Text(
                text = "Deallocate Room",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Occupied Room Dropdown
            var occupiedExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = occupiedExpanded,
                onExpandedChange = { occupiedExpanded = !occupiedExpanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                OutlinedTextField(
                    value = selectedOccupiedRoom?.let { 
                        "${it.student.name} - ${it.room.hostelName} ${it.room.roomNumber}" 
                    } ?: "Select Occupied Room/Student",
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = occupiedExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color(0xFFE0E0E0)
                    )
                )
                ExposedDropdownMenu(
                    expanded = occupiedExpanded,
                    onDismissRequest = { occupiedExpanded = false }
                ) {
                    occupiedRooms.forEach { occupiedRoom ->
                        DropdownMenuItem(
                            text = { 
                                Text("${occupiedRoom.student.name} - ${occupiedRoom.room.hostelName} ${occupiedRoom.room.roomNumber}")
                            },
                            onClick = {
                                selectedOccupiedRoom = occupiedRoom
                                occupiedExpanded = false
                            }
                        )
                    }
                }
            }
            
            // Deallocate Button
            Button(
                onClick = {
                    // Handle deallocation logic
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 80.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF90CAF9)
                ),
                enabled = selectedOccupiedRoom != null
            ) {
                Text(
                    text = "Deallocate",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1976D2)
                )
            }
        }
    }
}
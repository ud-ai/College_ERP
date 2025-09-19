package com.example.collegeerp.data.repository

import com.example.collegeerp.data.local.AppDatabase
import com.example.collegeerp.data.local.entity.RoomEntity
import com.example.collegeerp.domain.model.HostelOccupancy
import com.example.collegeerp.domain.model.HostelRoom
import com.example.collegeerp.domain.repository.HostelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HostelRepositoryImpl @Inject constructor(
    private val db: AppDatabase
) : HostelRepository {
    override fun observeRooms(hostelId: String): Flow<List<HostelRoom>> {
        return db.roomDao().observeRoomsByHostel(hostelId).map { rooms ->
            rooms.map { it.toDomain(emptyList()) } // occupancy joined later if needed
        }
    }
}

private fun RoomEntity.toDomain(occupancies: List<HostelOccupancy>): HostelRoom = HostelRoom(
    hostelId = hostelId,
    roomId = roomId,
    roomNo = roomNo,
    capacity = capacity,
    occupancy = occupancies
)



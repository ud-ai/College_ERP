package com.example.collegeerp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

data class Hostel(
    val name: String,
    val capacity: Int,
    val imageRes: String = ""
)

data class Room(
    val number: String,
    val capacity: Int,
    val occupied: Int,
    val status: RoomStatus
)

enum class RoomStatus {
    AVAILABLE, PARTIAL, FULL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostelRoomsScreen(
    onBack: () -> Unit = {}
) {
    val hostels = remember {
        listOf(
            Hostel("Boys Hostel", 200),
            Hostel("Girls Hostel", 150)
        )
    }
    
    val rooms = remember {
        listOf(
            Room("Room 101", 4, 2, RoomStatus.PARTIAL),
            Room("Room 102", 2, 1, RoomStatus.PARTIAL),
            Room("Room 103", 4, 0, RoomStatus.AVAILABLE),
            Room("Room 104", 4, 4, RoomStatus.FULL)
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
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
                text = "Hostel",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Hostels",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            items(hostels) { hostel ->
                HostelCard(hostel = hostel)
            }
            
            item {
                Text(
                    text = "Rooms (Boys Hostel)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            
            items(rooms) { room ->
                RoomCard(room = room)
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun HostelCard(hostel: Hostel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for hostel image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Color(0xFF2196F3).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = hostel.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                Text(
                    text = "Capacity: ${hostel.capacity}",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun RoomCard(room: Room) {
    val (backgroundColor, borderColor, iconColor, statusText, statusColor) = when (room.status) {
        RoomStatus.AVAILABLE -> listOf(
            Color(0xFFF0F9F0),
            Color(0xFF4CAF50),
            Color(0xFF4CAF50),
            "(Available)",
            Color(0xFF4CAF50)
        )
        RoomStatus.PARTIAL -> listOf(
            Color.White,
            Color(0xFF2196F3),
            Color(0xFF2196F3),
            "",
            Color(0xFF333333)
        )
        RoomStatus.FULL -> listOf(
            Color(0xFFFFF0F0),
            Color(0xFFF44336),
            Color(0xFFF44336),
            "(Full)",
            Color(0xFFF44336)
        )
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor as Color),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = if (room.status != RoomStatus.PARTIAL) {
            androidx.compose.foundation.BorderStroke(1.dp, borderColor as Color)
        } else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = (iconColor as Color).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = room.number,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                Text(
                    text = "Capacity: ${room.capacity}",
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                if (room.status != RoomStatus.AVAILABLE) {
                    // Progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .padding(top = 8.dp)
                            .background(
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(3.dp)
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(room.occupied.toFloat() / room.capacity)
                                .fillMaxHeight()
                                .background(
                                    color = iconColor,
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )
                    }
                }
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${room.occupied}/${room.capacity}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                if ((statusText as String).isNotEmpty()) {
                    Text(
                        text = statusText,
                        fontSize = 12.sp,
                        color = statusColor as Color,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}
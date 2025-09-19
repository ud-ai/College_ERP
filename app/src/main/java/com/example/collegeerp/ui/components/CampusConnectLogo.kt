package com.example.collegeerp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CampusConnectLogo(
    modifier: Modifier = Modifier,
    campusColor: Color = Color(0xFF8BC34A),
    connectColor: Color = Color(0xFF2196F3)
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CAMPUS",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = campusColor,
                letterSpacing = 2.sp
            )
            Text(
                text = "CONNECT",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = connectColor,
                letterSpacing = 2.sp
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        // WiFi Signal Icon
        Canvas(
            modifier = Modifier.size(40.dp)
        ) {
            drawWiFiIcon(campusColor)
        }
    }
}

private fun DrawScope.drawWiFiIcon(color: Color) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    
    // Inner dot
    drawCircle(
        color = color,
        radius = 3.dp.toPx(),
        center = Offset(centerX, centerY + 8.dp.toPx())
    )
    
    // First arc
    val path1 = Path().apply {
        addArc(
            oval = androidx.compose.ui.geometry.Rect(
                centerX - 8.dp.toPx(),
                centerY,
                centerX + 8.dp.toPx(),
                centerY + 16.dp.toPx()
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f
        )
    }
    drawPath(
        path = path1,
        color = color.copy(alpha = 0.8f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
    )
    
    // Second arc
    val path2 = Path().apply {
        addArc(
            oval = androidx.compose.ui.geometry.Rect(
                centerX - 16.dp.toPx(),
                centerY - 8.dp.toPx(),
                centerX + 16.dp.toPx(),
                centerY + 24.dp.toPx()
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f
        )
    }
    drawPath(
        path = path2,
        color = color.copy(alpha = 0.6f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
    )
    
    // Third arc
    val path3 = Path().apply {
        addArc(
            oval = androidx.compose.ui.geometry.Rect(
                centerX - 24.dp.toPx(),
                centerY - 16.dp.toPx(),
                centerX + 24.dp.toPx(),
                centerY + 32.dp.toPx()
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f
        )
    }
    drawPath(
        path = path3,
        color = color.copy(alpha = 0.4f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
    )
}
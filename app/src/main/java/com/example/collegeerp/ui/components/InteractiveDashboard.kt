package com.example.collegeerp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun InteractiveStatsCard(
    title: String,
    value: String,
    change: String,
    isPositive: Boolean,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { 
                isPressed = !isPressed
                onClick() 
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPressed) 8.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isPositive) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = if (isPositive) Color(0xFF4CAF50) else Color(0xFFF44336),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = change,
                        fontSize = 12.sp,
                        color = if (isPositive) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
            }
            
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = color.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun AnimatedProgressRing(
    progress: Float,
    color: Color,
    backgroundColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
    strokeWidth: Float = 12f,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = EaseInOutCubic),
        label = "progress"
    )
    
    Canvas(
        modifier = modifier.size(120.dp)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = (size.minDimension - strokeWidth) / 2
        
        // Background circle
        drawCircle(
            color = backgroundColor,
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        
        // Progress arc
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * animatedProgress,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2)
        )
    }
}

@Composable
fun LiveMetricsCard(
    title: String,
    metrics: List<Pair<String, Float>>,
    modifier: Modifier = Modifier
) {
    var selectedMetric by remember { mutableStateOf(0) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                metrics.forEachIndexed { index, (label, value) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { selectedMetric = index }
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (selectedMetric == index) 
                                    MaterialTheme.colorScheme.primaryContainer 
                                else Color.Transparent
                            )
                            .padding(8.dp)
                    ) {
                        AnimatedProgressRing(
                            progress = value / 100f,
                            color = when (index) {
                                0 -> MaterialTheme.colorScheme.primary
                                1 -> MaterialTheme.colorScheme.secondary
                                else -> MaterialTheme.colorScheme.tertiary
                            },
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${value.toInt()}%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionGrid(
    actions: List<Triple<String, ImageVector, () -> Unit>>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Quick Actions",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(200.dp)
            ) {
                items(actions) { action ->
                    val (title, icon, onClick) = action
                    var isPressed by remember { mutableStateOf(false) }
                    
                    Surface(
                        onClick = { 
                            isPressed = true
                            onClick()
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isPressed) 
                            MaterialTheme.colorScheme.primaryContainer 
                        else MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    LaunchedEffect(isPressed) {
                        if (isPressed) {
                            kotlinx.coroutines.delay(150)
                            isPressed = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrendIndicator(
    value: Float,
    previousValue: Float,
    label: String,
    modifier: Modifier = Modifier
) {
    val trend = value - previousValue
    val isPositive = trend >= 0
    val percentage = if (previousValue != 0f) (trend / previousValue * 100) else 0f
    
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isPositive) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = if (isPositive) Color(0xFF4CAF50) else Color(0xFFF44336),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = "${if (isPositive) "+" else ""}${percentage.toInt()}%",
            fontSize = 12.sp,
            color = if (isPositive) Color(0xFF4CAF50) else Color(0xFFF44336),
            fontWeight = FontWeight.Medium
        )
        Text(
            text = " $label",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
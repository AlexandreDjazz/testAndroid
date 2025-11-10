package com.bankingapp.ui.screens.map

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bankingapp.data.model.ATM
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ATMMapScreen(
    onBack: () -> Unit,
    viewModel: ATMMapViewModel = hiltViewModel()
) {
    val atmLocations by viewModel.atmLocations.collectAsState()
    val userLocation by viewModel.userLocation.collectAsState()

    // Request location permissions
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // Default location (Paris, France for demo)
    val defaultLocation = LatLng(48.8566, 2.3522)
    val currentLocation = userLocation?.let { LatLng(it.first, it.second) } ?: defaultLocation

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 14f)
    }

    LaunchedEffect(Unit) {
        if (locationPermissions.allPermissionsGranted) {
            // Load nearby ATMs with default location or actual location
            viewModel.loadNearbyATMs(currentLocation.latitude, currentLocation.longitude)
        } else {
            locationPermissions.launchMultiplePermissionRequest()
            // Use default location if permissions not granted
            viewModel.loadNearbyATMs(defaultLocation.latitude, defaultLocation.longitude)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find ATM") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Map
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(
                        isMyLocationEnabled = locationPermissions.allPermissionsGranted
                    ),
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = false,
                        myLocationButtonEnabled = false
                    )
                ) {
                    // User location marker
                    Marker(
                        state = MarkerState(position = currentLocation),
                        title = "Your Location",
                        snippet = "You are here"
                    )

                    // ATM markers
                    atmLocations.forEach { atm ->
                        Marker(
                            state = MarkerState(position = LatLng(atm.latitude, atm.longitude)),
                            title = atm.name,
                            snippet = "${atm.address} • ${String.format("%.2f", atm.distance)} km",
                            icon = if (atm.isAvailable) null else com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(
                                com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED
                            )
                        )
                    }
                }

                // My Location Button
                FloatingActionButton(
                    onClick = {
                        if (locationPermissions.allPermissionsGranted) {
                            // Recenter map to user location
                            viewModel.loadNearbyATMs(currentLocation.latitude, currentLocation.longitude)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "My Location",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // ATM List
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Nearby ATMs (${atmLocations.size})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(atmLocations) { atm ->
                            ATMListItem(atm = atm)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ATMListItem(atm: ATM) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (atm.isAvailable)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.errorContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = if (atm.isAvailable)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onErrorContainer
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = atm.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = atm.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = if (atm.isAvailable)
                            Color(0xFF4CAF50).copy(alpha = 0.1f)
                        else
                            MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = if (atm.isAvailable) "Available" else "Unavailable",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (atm.isAvailable)
                                Color(0xFF4CAF50)
                            else
                                MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "${String.format("%.2f", atm.distance)} km",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

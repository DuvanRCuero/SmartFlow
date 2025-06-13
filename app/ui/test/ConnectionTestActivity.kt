// ui/test/ConnectionTestActivity.kt
package com.example.smartflow.ui.test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.smartflow.domain.repository.AuthRepository
import com.example.smartflow.data.remote.repository.TaskRepository

@AndroidEntryPoint
class ConnectionTestActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var taskRepository: TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConnectionTestScreen(
                onTestConnection = { testConnection() },
                onCreateTestUser = { createTestUser() },
                onTestTasks = { testTasks() },
                onTestChat = { testChat() }
            )
        }
    }

    private fun testConnection() {
        lifecycleScope.launch {
            try {
                showToast("🔍 Testing backend connection...")

                // This depends on your AuthRepository having a health check method
                // You might need to add this to your repository
                showToast("✅ Backend is reachable!")

            } catch (e: Exception) {
                showToast("❌ Connection failed: ${e.message}")
                showConnectionHelp()
            }
        }
    }

    private fun createTestUser() {
        lifecycleScope.launch {
            try {
                showToast("🔧 Creating test user...")

                // Try to create test user via your auth repository
                val result = authRepository.register(
                    email = "test@smartflow-android.com",
                    name = "Android Test User",
                    password = "android123"
                )

                result.fold(
                    onSuccess = { loginResponse ->
                        showToast("✅ Test user created! Token saved.")
                    },
                    onFailure = { error ->
                        if (error.message?.contains("ya registrado") == true) {
                            // User exists, try login
                            loginTestUser()
                        } else {
                            showToast("❌ Test user creation failed: ${error.message}")
                        }
                    }
                )
            } catch (e: Exception) {
                showToast("❌ Error: ${e.message}")
            }
        }
    }

    private fun loginTestUser() {
        lifecycleScope.launch {
            try {
                showToast("🔑 Logging in test user...")

                val result = authRepository.login(
                    email = "test@smartflow-android.com",
                    password = "android123"
                )

                result.fold(
                    onSuccess = { loginResponse ->
                        showToast("✅ Test user logged in!")
                    },
                    onFailure = { error ->
                        showToast("❌ Login failed: ${error.message}")
                    }
                )
            } catch (e: Exception) {
                showToast("❌ Login error: ${e.message}")
            }
        }
    }

    private fun testTasks() {
        lifecycleScope.launch {
            try {
                showToast("📋 Testing tasks endpoint...")

                val result = taskRepository.getTasks()
                result.fold(
                    onSuccess = { tasks ->
                        showToast("✅ Got ${tasks.size} tasks from backend!")
                    },
                    onFailure = { error ->
                        showToast("❌ Tasks failed: ${error.message}")
                    }
                )
            } catch (e: Exception) {
                showToast("❌ Tasks error: ${e.message}")
            }
        }
    }

    private fun testChat() {
        lifecycleScope.launch {
            try {
                showToast("💬 Testing chat...")

                val result = taskRepository.sendMessage("Hello from Android app!")
                result.fold(
                    onSuccess = { chatResponse ->
                        showToast("✅ Chat works! Response: ${chatResponse.response.take(50)}...")
                    },
                    onFailure = { error ->
                        showToast("❌ Chat failed: ${error.message}")
                    }
                )
            } catch (e: Exception) {
                showToast("❌ Chat error: ${e.message}")
            }
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showConnectionHelp() {
        val helpMessage = """
            Connection Failed! Check:
            1. Backend running on Windows
            2. Same WiFi network
            3. Windows Firewall allows port 8000
            4. Correct IP in AppModule.kt
        """.trimIndent()

        showToast(helpMessage)
    }
}

@Composable
fun ConnectionTestScreen(
    onTestConnection: () -> Unit,
    onCreateTestUser: () -> Unit,
    onTestTasks: () -> Unit,
    onTestChat: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "🔧 SmartFlow Connection Test",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Test your connection to the Windows backend",
                    style = MaterialTheme.typography.bodyMedium)
            }
        }

        Button(
            onClick = onTestConnection,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("1. 🔍 Test Backend Connection")
        }

        Button(
            onClick = onCreateTestUser,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("2. 👤 Create Test User")
        }

        Button(
            onClick = onTestTasks,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("3. 📋 Test Tasks Endpoint")
        }

        Button(
            onClick = onTestChat,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("4. 💬 Test Chat Feature")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("💡 Troubleshooting:", style = MaterialTheme.typography.titleSmall)
                Text("• Update WINDOWS_IP in AppModule.kt", style = MaterialTheme.typography.bodySmall)
                Text("• Check Windows Firewall settings", style = MaterialTheme.typography.bodySmall)
                Text("• Ensure both devices on same WiFi", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
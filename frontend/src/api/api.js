// Placeholder API service for future Spring Boot integration.
// Keep the current dashboard static and local until MQTT/backend wiring is added.

export const api = {
  async getDashboardData() {
    return {
      sensorData: [],
      alerts: [],
      controls: {
        waterPumpOn: true,
        mistingOn: false,
      },
    }
  },

  async getSensors() {
    return []
  },

  async getAlerts() {
    return []
  },

  async updateSystemControl() {
    return { success: true }
  },
}

// Example future Spring Boot usage:
// export const dashboardApi = {
//   async getDashboardData() {
//     const response = await fetch('/api/dashboard')
//     return response.json()
//   },
// }

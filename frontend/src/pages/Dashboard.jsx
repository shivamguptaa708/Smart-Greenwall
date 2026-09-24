import { useState } from 'react'
import SensorCard from '../components/SensorCard.jsx'
import PollutionChart from '../components/PollutionChart.jsx'
import AlertList from '../components/AlertList.jsx'
import PumpControl from '../components/PumpControl.jsx'

const sensorData = [
  {
    label: 'PM1',
    value: 24,
    unit: 'µg/m³',
    status: 'Low',
    tone: 'good',
    delta: '+2% vs. avg',
    primary: false,
    variant: 'mint',
    spark: [18, 20, 22, 21, 24, 24],
  },
  {
    label: 'PM2.5',
    value: 42,
    unit: 'µg/m³',
    status: 'Moderate',
    tone: 'warning',
    delta: '+8% today',
    primary: true,
    variant: 'amber',
    spark: [30, 34, 39, 41, 43, 42],
  },
  {
    label: 'PM10',
    value: 76,
    unit: 'µg/m³',
    status: 'Elevated',
    tone: 'danger',
    delta: '+14% today',
    primary: false,
    variant: 'coral',
    spark: [52, 57, 61, 68, 72, 76],
  },
  {
    label: 'Temperature',
    value: 28.7,
    unit: '°C',
    status: 'Normal',
    tone: 'good',
    delta: '+0.3°C',
    primary: false,
    variant: 'orange',
    spark: [26, 26.5, 27.2, 28.1, 28.7, 28.9],
  },
  {
    label: 'Humidity',
    value: 68,
    unit: '%',
    status: 'Stable',
    tone: 'good',
    delta: '+2% RH',
    primary: false,
    variant: 'blue',
    spark: [61, 63, 64, 66, 68, 68],
  },
]

const alertData = [
  { title: 'PM2.5 concentration elevated', reading: '42 µg/m³', time: '2 min ago', severity: 'High' },
  { title: 'PM10 concentration elevated', reading: '76 µg/m³', time: '8 min ago', severity: 'Medium' },
  { title: 'Temperature above preferred range', reading: '28.7 °C', time: '12 min ago', severity: 'Medium' },
]

function BrandMark() {
  return (
    <span className="brand-mark" aria-hidden="true">
      <span className="brand-mark-inner" />
    </span>
  )
}

function Dashboard() {
  const [waterPumpOn, setWaterPumpOn] = useState(true)
  const [mistingOn, setMistingOn] = useState(false)

  return (
    <div className="app-shell">
      <header className="top-header">
        <div className="header-title-wrap">
          <BrandMark />
          <div>
            <div className="header-title">Smart Green Wall</div>
            <div className="header-subtitle">Pollution Control & Environmental Monitoring</div>
          </div>
        </div>

        <div className="header-meta">
          <span className="system-status">System Online</span>
          <span className="timestamp">Last updated: 2026-09-23 09:42</span>
        </div>
      </header>

      <main className="dashboard-main">
        <section className="panel-section">
          <div className="section-heading">
            <h2>Overview</h2>
          </div>

          <div className="sensor-grid">
            {sensorData.map((sensor) => (
              <SensorCard key={sensor.label} {...sensor} />
            ))}
          </div>
        </section>

        <section className="panel-section">
          <div className="section-heading">
            <h2>Pollution Trends</h2>
          </div>
          <PollutionChart />
        </section>

        <div className="bottom-row">
          <section className="panel-section control-panel">
            <div className="section-heading compact">
              <h2>System Controls</h2>
            </div>

            <div className="control-stack">
              <PumpControl
                label="Water Pump"
                enabled={waterPumpOn}
                onToggle={() => setWaterPumpOn((value) => !value)}
              />

              <PumpControl
                label="Misting System"
                enabled={mistingOn}
                onToggle={() => setMistingOn((value) => !value)}
              />
            </div>
          </section>

          <section className="panel-section alert-panel">
            <div className="section-heading compact">
              <h2>Alerts</h2>
            </div>

            <AlertList alerts={alertData} />
          </section>
        </div>
      </main>
    </div>
  )
}

export default Dashboard

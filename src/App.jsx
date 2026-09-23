import { useState } from 'react'
import './App.css'

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

const trendSeries = {
  labels: ['00:00', '04:00', '08:00', '12:00', '16:00', '20:00'],
  pm1: [16, 18, 22, 24, 20, 19],
  pm25: [28, 32, 41, 48, 46, 42],
  pm10: [44, 52, 63, 78, 72, 68],
}

const alertData = [
  { title: 'PM2.5 concentration elevated', reading: '42 µg/m³', time: '2 min ago', severity: 'High' },
  { title: 'PM10 concentration elevated', reading: '76 µg/m³', time: '8 min ago', severity: 'Medium' },
  { title: 'Temperature above preferred range', reading: '28.7 °C', time: '12 min ago', severity: 'Medium' },
]

const timeRanges = ['6H', '12H', '24H']

function BrandMark() {
  return (
    <span className="brand-mark" aria-hidden="true">
      <span className="brand-mark-inner" />
    </span>
  )
}

function StatusBadge({ status, tone = 'neutral' }) {
  return <span className={`status-badge ${tone}`}>{status}</span>
}

function Sparkline({ values, tone = 'primary' }) {
  const width = 90
  const height = 36
  const min = Math.min(...values)
  const max = Math.max(...values)
  const range = max - min || 1

  const points = values
    .map((value, index) => {
      const x = (index / (values.length - 1)) * width
      const y = height - ((value - min) / range) * (height - 4) - 2
      return `${x},${y}`
    })
    .join(' ')

  return (
    <svg viewBox={`0 0 ${width} ${height}`} className={`sparkline ${tone}`} aria-hidden="true">
      <polyline points={points} />
    </svg>
  )
}

function SensorCard({ label, value, unit, status, tone, delta, primary, variant, spark }) {
  return (
    <article className={`sensor-card ${primary ? 'primary' : ''} ${variant}`}>
      <div className="sensor-top-row">
        <span className="sensor-name">{label}</span>
        <StatusBadge status={status} tone={tone} />
      </div>

      <div className="sensor-value-row">
        <span className="sensor-value">{value}</span>
        <span className="sensor-unit">{unit}</span>
      </div>

      <div className="sensor-footer">
        <span className="sensor-trend">{delta}</span>
        <Sparkline values={spark} tone={tone} />
      </div>
    </article>
  )
}

function TrendChart() {
  const [selectedRange, setSelectedRange] = useState('24H')
  const maxValue = 100
  const chartLeft = 76
  const chartRight = 610
  const chartTop = 20
  const chartBottom = 168
  const xStep = 108

  const scaleY = (value) => chartBottom - (value / maxValue) * (chartBottom - chartTop)
  const pointMap = (series) =>
    series
      .map((value, index) => {
        const x = chartLeft + index * xStep
        const y = scaleY(value)
        return `${x},${y}`
      })
      .join(' ')

  const pm1Points = pointMap(trendSeries.pm1)
  const pm25Points = pointMap(trendSeries.pm25)
  const pm10Points = pointMap(trendSeries.pm10)

  return (
    <div className="chart-panel">
      <div className="chart-meta">
        <div>
          <div className="chart-label-title">Pollution trends</div>
          <div className="chart-subtitle">Last 24 hours</div>
        </div>

        <div className="range-picker" aria-label="Time range selector">
          {timeRanges.map((range) => (
            <button
              key={range}
              type="button"
              className={`range-option ${selectedRange === range ? 'active' : ''}`}
              onClick={() => setSelectedRange(range)}
            >
              {range}
            </button>
          ))}
        </div>
      </div>

      <div className="chart-legend">
        <span><i className="legend-dot pm1"></i>PM1</span>
        <span><i className="legend-dot pm25"></i>PM2.5</span>
        <span><i className="legend-dot pm10"></i>PM10</span>
      </div>

      <svg viewBox="0 0 640 220" className="trend-chart" role="img" aria-label="PM1, PM2.5 and PM10 concentration chart">
        <text x="18" y="108" transform="rotate(-90 18 108)" className="axis-label">
          Concentration (µg/m³)
        </text>

        {[0, 20, 40, 60, 80, 100].map((value) => {
          const y = scaleY(value)
          return (
            <g key={value}>
              <line x1={chartLeft} x2={chartRight} y1={y} y2={y} stroke="rgba(18, 58, 46, 0.08)" strokeWidth="1" />
              <text x={34} y={y + 4} className="grid-label">{value}</text>
            </g>
          )
        })}

        <polyline points={pm1Points} fill="none" stroke="#16A36A" strokeWidth="3" strokeLinejoin="round" strokeLinecap="round">
          <title>PM1</title>
        </polyline>
        <circle cx={600} cy={scaleY(trendSeries.pm1[trendSeries.pm1.length - 1])} r="4" fill="#16A36A" />

        <polyline points={pm25Points} fill="none" stroke="#0B3D2E" strokeWidth="3.5" strokeLinejoin="round" strokeLinecap="round">
          <title>PM2.5</title>
        </polyline>
        <circle cx={600} cy={scaleY(trendSeries.pm25[trendSeries.pm25.length - 1])} r="4.5" fill="#0B3D2E" />

        <polyline points={pm10Points} fill="none" stroke="#F4A340" strokeWidth="3" strokeLinejoin="round" strokeLinecap="round">
          <title>PM10</title>
        </polyline>
        <circle cx={600} cy={scaleY(trendSeries.pm10[trendSeries.pm10.length - 1])} r="4" fill="#F4A340" />

        {trendSeries.labels.map((label, index) => (
          <text key={label} x={chartLeft + index * xStep} y="205" textAnchor="middle" className="chart-x-label">
            {label}
          </text>
        ))}
      </svg>
    </div>
  )
}

function ControlSwitch({ label, enabled, onToggle }) {
  return (
    <div className="control-row">
      <div className="control-copy">
        <div className="control-label">{label}</div>
        <div className="control-state-wrap">
          <span className="mini-status-dot" aria-hidden="true" />
          <span className={`control-state ${enabled ? 'on' : 'off'}`}>{enabled ? 'On' : 'Off'}</span>
        </div>
      </div>

      <button
        type="button"
        className={`toggle ${enabled ? 'enabled' : ''}`}
        onClick={onToggle}
        aria-label={`${label} ${enabled ? 'enabled' : 'disabled'}`}
      >
        <span className="toggle-thumb" />
      </button>
    </div>
  )
}

function App() {
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
          <TrendChart />
        </section>

        <div className="bottom-row">
          <section className="panel-section control-panel">
            <div className="section-heading compact">
              <h2>System Controls</h2>
            </div>

            <div className="control-stack">
              <ControlSwitch
                label="Water Pump"
                enabled={waterPumpOn}
                onToggle={() => setWaterPumpOn((value) => !value)}
              />

              <ControlSwitch
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

            <div className="alert-list">
              {alertData.map((alert) => (
                <div key={alert.title} className="alert-item">
                  <span className={`alert-severity ${alert.severity.toLowerCase()}`}>{alert.severity}</span>
                  <div className="alert-copy">
                    <div className="alert-title">{alert.title}</div>
                    <div className="alert-meta-row">
                      <span>{alert.reading}</span>
                      <span>•</span>
                      <span>{alert.time}</span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </section>
        </div>
      </main>
    </div>
  )
}

export default App

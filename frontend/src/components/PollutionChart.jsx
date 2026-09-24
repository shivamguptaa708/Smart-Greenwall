import { useState } from 'react'

const timeRanges = ['6H', '12H', '24H']

const trendSeries = {
  labels: ['00:00', '04:00', '08:00', '12:00', '16:00', '20:00'],
  pm1: [16, 18, 22, 24, 20, 19],
  pm25: [28, 32, 41, 48, 46, 42],
  pm10: [44, 52, 63, 78, 72, 68],
}

function PollutionChart() {
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

export default PollutionChart

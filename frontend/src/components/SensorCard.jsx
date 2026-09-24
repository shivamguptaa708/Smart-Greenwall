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

export default SensorCard

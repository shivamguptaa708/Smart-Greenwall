function AlertList({ alerts }) {
  return (
    <div className="alert-list">
      {alerts.map((alert) => (
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
  )
}

export default AlertList

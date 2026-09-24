function ThresholdControl({ label, value, unit, enabled, onToggle }) {
  return (
    <div className="control-row">
      <div className="control-copy">
        <div className="control-label">{label}</div>
        <div className="control-state-wrap">
          <span className="mini-status-dot" aria-hidden="true" />
          <span className={`control-state ${enabled ? 'on' : 'off'}`}>{enabled ? 'Active' : 'Standby'}</span>
          {value !== undefined && (
            <span className="control-threshold-value">
              {value}
              {unit}
            </span>
          )}
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

export default ThresholdControl

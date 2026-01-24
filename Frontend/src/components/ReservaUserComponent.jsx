import { useContext, useEffect, useState } from "react"
import { DestinosContext } from "../context/Destinos/DestinosContext"
import '../styles/ReservaUserComponent.css'

export const ReservaUserComponent = ({ reserva }) => {

    const { buscarDestinosPorVuelo } = useContext(DestinosContext)
    const [misVuelos, setMisVuelos] = useState([])

    useEffect(() => {
        const cargarDestinos = async () => {
            setMisVuelos(await buscarDestinosPorVuelo(reserva.startFlight))
        }
        cargarDestinos()
    }, [reserva.startFlight])

    if (!reserva || !misVuelos || misVuelos.length === 0) {
        return (
            <div className="reservation-card">
                <div className="reservation-header">
                    <h3>Cargando Reserva...</h3>
                </div>
            </div>
        )
    }

    return (
        <div className="reservation-card">

            <div className="reservation-image">
                <img src={misVuelos[1].images[0]} alt={misVuelos[1].name} />
            </div>

            <div className="reservation-content">
                <div>
                    <div className="reservation-header">
                        <h3>{misVuelos[0].name} → {misVuelos[1].name}</h3>
                        <span className={`status ${reserva.reservationStatus.toLowerCase()}`}>
                            {reserva.reservationStatus}
                        </span>
                    </div>

                    <p className="flight-type">{reserva.returnFlight ? "Ida y Vuelta" : "Solo Ida"}</p>

                    <div className="reservation-details">
                        <span>Económica: <strong>{reserva.economyClass}</strong></span>
                        <span>Premium Económica: <strong>{reserva.premium_economyClass}</strong></span>
                        <span>Business: <strong>{reserva.businessClass}</strong></span>
                        <span>First: <strong>{reserva.firstClass}</strong></span>
                    </div>
                </div>

                <div className="reservation-footer">
                    <div className="reservation-meta">
                        <span>Reserva #{reserva.id}</span>
                        <span>{new Date(reserva.creationDate).toLocaleDateString()}</span>
                    </div>

                    <button
                        className="pay-button"
                        // onClick={handlePay}
                        disabled={reserva.reservationStatus !== "CREATED"}
                    >
                        Pagar
                    </button>
                </div>
            </div>
        </div>
    )
}

import { useContext, useEffect, useState } from "react"
import { DestinosContext } from "../context/Destinos/DestinosContext"
import '../styles/ReservaUserComponent.css'
import { CrearReviewComponent } from "./CrearReviewComponent"
import { NavLink } from "react-router-dom"

export const ReservaUserComponent = ({ reserva, actualizarReviews, cancelarReserva }) => {

    const { buscarDestinosPorVuelo } = useContext(DestinosContext)
    const [misVuelos, setMisVuelos] = useState([])
    const [openCrearReview, setOpenCrearReview] = useState(false)
    const [openCancelarReview, setOpenCacenlarReview] = useState(false)

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
                <NavLink to={`/destinoinfo/${misVuelos[1].id}`} className='logo myLink'>
                    <img src={misVuelos[1].images[0]} alt={misVuelos[1].name} />
                </NavLink>
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
                        className="review-button"
                        onClick={() => setOpenCrearReview(!openCrearReview)}
                    >
                        Añadir Reseña
                    </button>

                    <button
                        className="pay-button"
                        // onClick={Pagar}
                        disabled={reserva.reservationStatus !== "CREATED"}
                    >
                        Pagar
                    </button>

                    <button
                        className="cancelar-button"
                        onClick={() => setOpenCacenlarReview(true)}
                        disabled={reserva.reservationStatus !== "CREATED"}
                    >
                        Cancelar
                    </button>

                    {
                        openCancelarReview &&
                        <div className="editar-form">
                            <div className="editar-container">
                                <h2 className="form-title">{`¿Seguro que quiere cancelar su reservar un vuelo de ${misVuelos[0].name} a ${misVuelos[1].name}?`}</h2>
                                <div className="botones-editar">
                                    <button className='edit-button' onClick={() => setOpenCacenlarReview(false)}>Mantener Reserva</button>
                                    <button className='delete-button' onClick={() => cancelarReserva(reserva.id)}>Cancelar</button>
                                </div>
                            </div>
                        </div>
                    }

                    {openCrearReview &&
                        <CrearReviewComponent onClose={setOpenCrearReview} destination_id={misVuelos[1].id} actualizarReviews={actualizarReviews} />}
                </div>
            </div>
        </div>
    )
}

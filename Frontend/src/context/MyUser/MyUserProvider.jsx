import { useState } from "react"
import { MyUserContext } from "./MyUserContext"

export const MyUserProvider = ({ children }) => {

    const address = "http://localhost:8081/myuser"

    const crearReserva = async (token, reserva) => {
        try {
            const res = await fetch(`${address}/reserva`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(reserva)
            })
        } catch (error) {
            console.log(error)
        }
    }

    const [misReservas, setMisReservas] = useState([])

    const verMiReservas = async (token) => {
        try {
            const res = await fetch(`${address}/reserva`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setMisReservas(data)
        } catch (error) {
            console.log(error)
        }
    }

    const [miReservaPorId, setMiReservaPorId] = useState({})

    const buscarMiReservaPorId = async (id, token) => {
        try {
            const res = await fetch(`${address}/reserva/${id}`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setMiReservaPorId(data)
        } catch (error) {
            console.log(error)
        }
    }

    const actualizarMiReserva = async (reserva, token) => {
        try {
            const res = await fetch(`${address}/reserva`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(reserva)
            })
        } catch (error) {
            console.log(error)
        }
    }

    const eliminarMiReservaPorId = async (token, id) => {
        try {
            const res = await fetch(`${address}/reserva/${id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
        } catch (error) {
            console.log(error)
        }
    }



    const crearPasajero = async (token, pasajero) => {
        try {
            const res = await fetch(`${address}/pasajero`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(pasajero)
            })
        } catch (error) {
            console.log(error)
        }
    }

    const [misPasajeros, setMisPasajeros] = useState([])

    const verMipasajeros = async (token) => {
        try {
            const res = await fetch(`${address}/pasajero`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setMisPasajeros(data)
        } catch (error) {
            console.log(error)
        }
    }

    const [miPasajeroPorId, setMiPasajeroPorId] = useState({})

    const buscarMiPasajeroPorId = async (id, token) => {
        try {
            const res = await fetch(`${address}/pasajero/${id}`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setMiPasajeroPorId(data)
        } catch (error) {
            console.log(error)
        }
    }

    const actualizarMiPasajeroPorId = async (pasajero, token) => {
        try {
            const res = await fetch(`${address}/pasajero`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(pasajero)
            })
        } catch (error) {
            console.log(error)
        }
    }

    const eliminarMiPasajero = async (id, token) => {
        try {
            const res = await fetch(`${address}/pasajero/${id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
        } catch (error) {
            console.log(error)
        }
    }


    const crearTicket = async (token, ticket) => {
        try {
            const res = await fetch(`${address}/ticket`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(ticket)
            })
        } catch (error) {
            console.log(error)
        }
    }

    const [misTickets, setMisTickets] = useState([])

    const verMiTickets = async (token) => {
        try {
            const res = await fetch(`${address}/ticket`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`,
                }
            })
            const data = await res.json()
            setMisTickets(data)
        } catch (error) {
            console.log(error)
        }
    }

    const [miTicketPorId, setMiTicketPorId] = useState({})

    const buscarMiTicketPorId = async (id, token) => {
        try {
            const res = await fetch(`${address}/ticket/${id}`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setMiTicketPorId(data)
        } catch (error) {
            console.log(error)
        }
    }

    const actualizarMiTicket = async (ticket, token) => {
        try {
            const res = await fetch(`${address}/ticket`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(ticket)
            })()
        } catch (error) {
            console.log(error)
        }
    }

    const eliminarMiTicketPorId = async (id, token) => {
        try {
            const res = await fetch(`${address}/ticket/${id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
        } catch (error) {
            console.log(error)
        }
    }

    const [destinosFavoritos, setDestinosFavoritos] = useState([])

    const getDestinosFavoritos = async (token) => {
        try {
            const res = await fetch(`${address}/favoritos`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setDestinosFavoritos(data)
        } catch (error) {
            console.log(error)
        }
    }


    const [misReviews, setMisReviews] = useState([])

    const getMisReviews = async (token) => {
        try {
            const res = await fetch(`${address}/review`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
            const data = await res.json()
            setMisReviews(data)
        } catch (error) {
            console.log(error)
        }
    }

    const crearReview = async (token, review) => {
        try {
            const res = await fetch(`${address}/review`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(review)
            })
        } catch (error) {
            console.log(error)
        }
    }

    const eliminarMiReviewPorId = async (token, id) => {
        try {
            const res = await fetch(`${address}/review/${id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <MyUserContext value={{
            crearReserva,
            misReservas,
            verMiReservas,
            miReservaPorId,
            buscarMiReservaPorId,
            actualizarMiReserva,
            eliminarMiReservaPorId,
            crearPasajero,
            misPasajeros,
            verMipasajeros,
            buscarMiPasajeroPorId,
            actualizarMiPasajeroPorId,
            eliminarMiPasajero,
            crearTicket,
            misTickets,
            verMiTickets,
            miTicketPorId,
            buscarMiTicketPorId,
            actualizarMiTicket,
            eliminarMiTicketPorId,
            destinosFavoritos,
            getDestinosFavoritos,
            misReviews,
            getMisReviews,
            crearReview,
            eliminarMiReviewPorId
        }}>
            {children}
        </MyUserContext>
    )
}

import { useEffect, useState } from "react"
import { DestinosContext } from "./DestinosContext"

export const DestinosProvider = ({ children }) => {

    const [destinos, setDestinos] = useState([])

    const fetchDestinos = async () => {
        try {
            const res = await fetch('http://localhost:8081/destinos')
            const data = await res.json()
            setDestinos(data)
        } catch (error) {
            console.error(error)
        }
    }


    const addDestinos = async (destino) => {
        try {
            const res = await fetch("http://localhost:8081/destinos", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(destino)
            });
            fetchDestinos();
            const data = await res.json()
            console.log("Respuesta:", data)

        } catch (error) {
            console.error("Error al enviar POST:", error)
        }
    }

    const eliminarDestinos = async (id) => {
        try {
            const res = await fetch(`http://localhost:8081/destinos/${id}`, {
                method: "DELETE"
            });

            if (!res.ok) {
                throw new Error("Error al eliminar")
            }
            fetchDestinos();
            console.log("Eliminado correctamente")
        } catch (error) {
            console.error("Error:", error)
        }
    }

    const editarDestinos = async (newDestino) => {
        try {
            const res = await fetch("http://localhost:8081/destinos", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(newDestino)
            });
            const data = await res.text();
            console.log("Respuesta:", data);
        } catch (error) {
            console.error("Error al Actualizar:", error);
        }
    }

    const [random, setRandom] = useState([])

    const destinosRandoms = async (nro) => {
        try {
            const res = await fetch(`http://localhost:8081/destinos/random/${nro}`)
            const data = await res.json()
            setRandom(data)
        } catch (error) {
            console.error(error)
        }
    }

    const [categoria, setCategoria] = useState([])

    const destinosCategorias = async (nro) => {
        if (nro != null) {
            try {
                const res = await fetch(`http://localhost:8081/destinos/categoria/${nro}`)
                const data = await res.json()
                setCategoria(data)
            } catch (error) {
                console.error(error)
            }
        } else {
            await fetchDestinos()
            setCategoria(destinos)
        }
    }

    const [destinoPorID, setDestinoPorID] = useState({})

    const buscarDestinoPorId = async (id) => {
        try {
            const res = await fetch(`http://localhost:8081/destinos/${id}`)
            const data = await res.json()
            setDestinoPorID(data)
        } catch (error) {
            console.error(error)
        }

    }

    const [destinoPorNombre, setDestinoPorNombre] = useState()

    const buscarDestinoPorNombre = async (nombre) => {
        try {
            const res = await fetch(`http://localhost:8081/destinos/nombre/${nombre}`)
            if (!res.ok) {
                const text = await res.text()
                setDestinoPorNombre({})
                return;
            }
            const data = await res.json()
            setDestinoPorNombre(data)
        } catch (error) {
            console.error(error)
        }

    }

    return (
        <DestinosContext value={{
            destinos, fetchDestinos,
            setDestinos, addDestinos,
            eliminarDestinos, editarDestinos,
            random, destinosRandoms,
            categoria, destinosCategorias,
            destinoPorID, buscarDestinoPorId,
            destinoPorNombre, buscarDestinoPorNombre
        }}>
            {children}
        </DestinosContext>

    )
}

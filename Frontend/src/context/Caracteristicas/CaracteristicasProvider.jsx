import { useState } from 'react'
import { CaracteristicasContext } from './CaracteristicasContext'

export const CaracteristicasProvider = ({ children }) => {

    const [caracteristicas, setCarateristicas] = useState([])

    const fetchCaracteristicas = async () => {
        try {
            const res = await fetch('http://localhost:8081/caracteristicas')
            const data = await res.json()
            setCarateristicas(data)
        } catch (error) {
            console.error(error)
        }
    }

    const addCaracteristicas = async (caracteristicas, token) => {
        try {
            const res = await fetch("http://localhost:8081/caracteristicas", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(caracteristicas)
            })
            fetchCaracteristicas()
            const data = await res.json()
        } catch (error) {
            console.error("Error al enviar POST:", error);
        }
    }

    const eliminarCaracteristicas = async (id, token) => {
        try {
            const res = await fetch(`http://localhost:8081/caracteristicas/${id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            })

            if (!res.ok) {
                throw new Error("Error al eliminar")
            }
            fetchCaracteristicas()
            console.log("Eliminado correctamente")
        } catch (error) {
            console.error("Error:", error)
        }
    }

    const editarCaracteristica = async (newCaracteristica, token) => {
        try {
            const res = await fetch("http://localhost:8081/caracteristicas", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(newCaracteristica)
            })
            const data = await res.text()
        } catch (error) {
            console.error("Error al Actualizar:", error)
        }
    }

    const [caracteristicaPorID, setCaracteristicaPorID] = useState({})

    const buscarCaracteristicaPorId = async (id) => {
        try {
            const res = await fetch(`http://localhost:8081/caracteristicas/${id}`)
            const data = await res.json()
            setCaracteristicaPorID(data)
        } catch (error) {
            console.error(error)
        }
    }


    return (
        <CaracteristicasContext value={{
            caracteristicas, setCarateristicas, fetchCaracteristicas,
            addCaracteristicas, eliminarCaracteristicas, editarCaracteristica,
            caracteristicaPorID, buscarCaracteristicaPorId
        }}>
            {children}
        </CaracteristicasContext>

    )
}
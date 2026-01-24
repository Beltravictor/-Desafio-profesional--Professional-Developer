import { useState } from 'react'
import { VuelosContext } from './VuelosContext'

export const VuelosProvider = ({ children }) => {

    const [vuelos, setVuelos] = useState([])

    const vuelosPorOrigenDestino = async (ori, des) => {
        try {
            const res = await fetch(`http://localhost:8081/vuelos/destinos/${ori}/${des}`, {
                method: "GET",
            })
            const data = await res.json()
            setVuelos(data)
        } catch (error) {
            console.error(error)
        }
    }

    return (
        <VuelosContext value={{
            vuelosPorOrigenDestino, vuelos
        }}>
            {children}
        </VuelosContext>

    )
}
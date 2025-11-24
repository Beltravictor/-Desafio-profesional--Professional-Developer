import { useState } from "react"

export const DestinoFormComponent = () => {

    const [fechadeIda, setFechadeIda] = useState()
    const [fechadeVuelta, setFechadeVuelta] = useState()
    const [pasajeros, setPasajeros] = useState([1,0,0])

    return (
        <div className='destinoform'>
            <div className='viaje'>
                <div>Origen</div>
                <span className='separador'></span>
                <div>Destino</div>
            </div>
            <span className='separador'></span>
            <div>Fecha de Ida
            </div>
            <span className='separador'></span>
            <div>Fecha de Vuelta </div>
            <span className='separador'></span>
            <div> Pasajeros </div>
            <span className='separador'></span>
            <button className='buscar'> Buscar </button>
        </div>
    )
}

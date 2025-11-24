import { useContext, useEffect, useState } from 'react'
import '../../styles/Vuelos.css'
import { RecomendacionesComponent } from '../../components/RecomendacionesComponent'
import { CategoriasComponent } from '../../components/CategoriasComponent'
import { DestinosContext } from '../../context/Destinos/DestinosContext'
import { DestinoFormComponent } from '../../components/DestinoFormComponent'

export const VuelosPage = () => {

    const { random, destinosRandoms } = useContext(DestinosContext)

    useEffect(() => {
        destinosRandoms(10)
    }, [])
    
    
    return (
        <>
            <div className='fondo'>
                <div></div>
                <img src="https://static.nationalgeographicla.com/files/styles/image_3200/public/nationalgeographic_2744265.jpg?w=1900&h=1267" alt="" />
            </div>

            <h1 className='title'>Elegí tu Nueva Aventura</h1>
            <DestinoFormComponent/>

            <h2 className='subtitle'>Buscar por Categoría</h2>
            <CategoriasComponent />

            <h2 className='subtitle'>Nuestas Recomendaciones</h2>
            <div className='grid'>
                {
                    random.map(ran => (
                        <RecomendacionesComponent destino={ran} key={ran.id} />
                    ))
                }
            </div>

        </>
    )
}

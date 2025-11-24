import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { DestinosContext } from '../../context/Destinos/DestinosContext';
import '../../styles/DestinoInfoPage.css'
import { HeaderComponent } from '../../components/HeaderComponent';

export const DestinoImagenesPage = () => {
    const { id } = useParams()

    const { destinoPorID, buscarDestinoPorId } = useContext(DestinosContext)
    const [imgs, setiImgs] = useState([])
    const [verMas, setVerMas] = useState(false)

    useEffect(() => {
        buscarDestinoPorId(id)
    }, [id])

    useEffect(() => {
        if (destinoPorID?.images) {
            setiImgs(destinoPorID.images);
        }
    }, [destinoPorID])

    if (!destinoPorID || Object.keys(destinoPorID).length === 0) {
        return <p>Cargando destino...</p>
    }

    return (
        <>
            <HeaderComponent name={destinoPorID.name} />
            {
                !verMas ?
                    <div className="gallery-container">
                        <div className="gallery-main">
                            <img src={imgs[0]} alt="Imagen principal" />
                        </div>

                        <div className="gallery-grid">
                            {imgs.slice(1, 5).map((img, i) => (
                                <div key={i} className="grid-item">
                                    <img src={img} alt={`Imagen ${i + 2}`} />
                                </div>
                            ))}
                        </div>

                        <button className='ver-mas-btn'
                            onClick={() => setVerMas(true)}
                        > Ver más</button>

                    </div>
                    :
                    <div>
                        <div className="gallery-grid">
                            {imgs.map((img, i) => (
                                <div key={i} className="grid-item">
                                    <img src={img} alt={`Imagen ${i + 2}`} />
                                </div>
                            ))}
                        </div>

                        <button className='ver-mas-btn'
                            onClick={() => setVerMas(false)}
                        > Ver menos</button>

                    </div>
            }

        </>

    )
}

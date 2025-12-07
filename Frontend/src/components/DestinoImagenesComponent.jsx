import { useState } from 'react'
import '../styles/DestinoInfoPage.css'

export const DestinoImagenesComponent = ({ destino }) => {

    const [verMas, setVerMas] = useState(false)

    return (
        <>
            {
                !verMas ?
                    <div className="gallery-container-ver-mas">
                        <div className="gallery-main">
                            <img src={destino.images[0]} alt="Imagen principal" />
                        </div>

                        <div className="gallery-grid">
                            {destino.images.slice(1, 5).map((img, i) => (
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
                    <div className="gallery-container-ver-menos">
                        <div className="gallery-grid">
                            {destino.images.map((img, i) => (
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

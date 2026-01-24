import '../styles/PoliticasComponent.css'

export const PoliticasComponent = ({ politicas }) => {
    return (
        <section className="politicas-container">
            <h2 className="politicas-title">Política de uso del producto</h2>

            <div className="politicas-grid">
                {politicas.map((politica, index) => (
                    <div className="politica-item" key={index}>
                        <h3 className="politica-item-title">{politica.title}</h3>
                        <p className="politica-item-description">
                            {politica.description}
                        </p>
                    </div>
                ))}
            </div>
        </section>
    );
};

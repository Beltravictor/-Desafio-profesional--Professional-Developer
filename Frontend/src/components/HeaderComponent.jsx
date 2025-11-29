import '../styles/HeaderComponent.css'

export const HeaderComponent = ({name = ''}) => {
    return (
        <header className="header-container">
            <h1 className="header-title">{name}</h1>
            <button className="header-back" onClick={() => window.history.back()}>
                {"<"}
            </button>
        </header>
    )
}

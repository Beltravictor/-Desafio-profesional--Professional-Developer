import "../styles/FooterComponent.css";

export const FooterComponent = () => {
  return (
    <footer className="footer-container">
      <div className="footer-left">
        <img
          src="src/assets/footer/Footer-img.png"
          alt="Isologotipo Empresa"
          className="footer-logo"
        />
        <p>
          © 2025 VuelosDH. Todos los derechos reservados.
        </p>
      </div>
    </footer>
  )
}
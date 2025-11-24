import { Route, Routes } from "react-router-dom"
import { NavBarComponent } from "./components/NavBarComponent"
import { DestinosPage } from "./pages/Destinos/DestinosPage"
import { DestinosProvider } from "./context/Destinos/DestinosProvider"
import { CategoriasProvider } from "./context/Categorias/CategoriasProvider"
import ScrollToTop from "./components/ScrollToTop"
import { DestinoImagenesPage } from "./pages/Destinos/DestinoImagenesPage"
import { FondoComponent } from "./components/FondoComponent"
import { DestinoDetallePage } from "./pages/Destinos/DestinoDetallePage"
import { AdministracionPage } from "./pages/Administracion/AdministracionPage"
import { AdministracionDestinosPage } from "./pages/Administracion/AdministracionDestinosPage"
import { AdministracionEditarDestinoPage } from "./pages/Administracion/AdministracionEditarDestinoPage"
import { AdministracionGuardarDestinoPage } from "./pages/Administracion/AdministracionGuardarDestinoPage"
import { AdministracionCategoriaPage } from "./pages/Administracion/AdministracionCategoriaPage"
import { AdministracionEditarCategoriaPage } from "./pages/Administracion/AdministracionEditarCategoriaPage"
import { AdministracionGuardarCategoriaPage } from "./pages/Administracion/AdministracionGuardarCategoriaPage"
import { VuelosPage } from "./pages/Vuelos/VuelosPage"
import { FooterComponent } from "./components/FooterComponent"


export const VuelosApp = () => {
  return (
    <DestinosProvider>
      <CategoriasProvider>
        <NavBarComponent />
        <ScrollToTop />
        <FondoComponent />
        <div style={{ height: "70px" }} />
        <Routes>
          <Route path="/" element={<VuelosPage />} />
          <Route path="/destinos" element={<DestinosPage />} />
          <Route path="/destinos/:filtro" element={<DestinosPage />} />
          <Route path="/destinos/:filtro/:id" element={<DestinosPage />} />
          <Route path="/destinoinfo/:id" element={<DestinoDetallePage />} />
          <Route path="/destinoinfo/imagenes/:id" element={<DestinoImagenesPage />} />
          <Route path="/administracion" element={<AdministracionPage />} />
          <Route path="/administracion/destinos" element={<AdministracionDestinosPage />} />
          <Route path="/administracion/destinos/:filtro" element={<AdministracionDestinosPage />} />
          <Route path="/administracion/destinos/:filtro/:id" element={<AdministracionDestinosPage />} />
          <Route path="/administracion/destinos/editar" element={<AdministracionEditarDestinoPage />} />
          <Route path="/administracion/destinos/editar/:id" element={<AdministracionEditarDestinoPage />} />
          <Route path="/administracion/destinos/crear" element={<AdministracionGuardarDestinoPage />} />
          <Route path="/administracion/categorias" element={<AdministracionCategoriaPage />} />
          <Route path="/administracion/categorias/editar/:id" element={<AdministracionEditarCategoriaPage />} />
          <Route path="/administracion/categorias/crear" element={<AdministracionGuardarCategoriaPage />} />
          <Route path="/*" element={<VuelosPage />} />
        </Routes>
        <FooterComponent />
      </CategoriasProvider>
    </DestinosProvider>
  )
}

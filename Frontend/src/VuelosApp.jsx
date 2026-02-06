import { Route, Routes } from "react-router-dom"
import { NavBarComponent } from "./components/NavBarComponent"
import { DestinosPage } from "./pages/Destinos/DestinosPage"
import { DestinosProvider } from "./context/Destinos/DestinosProvider"
import { CategoriasProvider } from "./context/Categorias/CategoriasProvider"
import ScrollToTop from "./components/ScrollToTop"
import { FondoComponent } from "./components/FondoComponent"
import { DestinoDetallePage } from "./pages/Destinos/DestinoDetallePage"
import { VuelosPage } from "./pages/Vuelos/VuelosPage"
import { FooterComponent } from "./components/FooterComponent"
import { RegistrarUsuarioPage } from "./pages/Usuario/RegistrarUsuarioPage"
import { UsuarioProvider } from "./context/Usuario/UsuarioProvider"
import { IniciarSesionUsuarioPage } from "./pages/Usuario/IniciarSesionUsuarioPage"
import { AuthProvider } from "./context/Usuario/AuthProvider"
import { PerfilUsuarioPage } from "./pages/Usuario/PerfilUsuarioPage"
import { CaracteristicasProvider } from "./context/Caracteristicas/CaracteristicasProvider"
import { AdministracionDestinosPage } from "./pages/Administracion/Destinos/AdministracionDestinosPage"
import { AdministracionGuardarDestinoPage } from "./pages/Administracion/Destinos/AdministracionGuardarDestinoPage"
import { AdministracionEditarDestinoPage } from "./pages/Administracion/Destinos/AdministracionEditarDestinoPage"
import { AdministracionCategoriaPage } from "./pages/Administracion/Categorias/AdministracionCategoriaPage"
import { AdministracionEditarCategoriaPage } from "./pages/Administracion/Categorias/AdministracionEditarCategoriaPage"
import { AdministracionGuardarCategoriaPage } from "./pages/Administracion/Categorias/AdministracionGuardarCategoriaPage"
import { AdministracionUserPage } from "./pages/Administracion/Usuario/AdministracionUserPage"
import { AdministracionPage } from "./pages/Administracion/AdministracionPage"
import { AdministracionCaracteristicasPage } from "./pages/Administracion/Caracteristicas/AdministracionCaracteristicasPage"
import { AdministracionEditarCaracteristicasPage } from "./pages/Administracion/Caracteristicas/AdministracionEditarCaracteristicasPage"
import { AdministracionGuardarCaracteristicasPage } from "./pages/Administracion/Caracteristicas/AdministracionGuardarCaracteristicasPage"
import { MyUserProvider } from "./context/MyUser/MyUserProvider"
import { ReservasPage } from "./pages/Reservas/ReservasPage"
import { VuelosProvider } from "./context/Vuelos/VuelosProvider"
import { WhatsappComponent } from "./components/WhatsappComponent"


export const VuelosApp = () => {
  return (
    <AuthProvider>
      <UsuarioProvider>
        <MyUserProvider>
          <VuelosProvider>
            <DestinosProvider>
              <CategoriasProvider>
                <CaracteristicasProvider>
                  <NavBarComponent />
                  <ScrollToTop />
                  <FondoComponent />
                  <div style={{ height: "70px" }} />
                  <Routes>
                    <Route path="/" element={<VuelosPage />} />
                    <Route path="/reservas" element={<ReservasPage />} />
                    <Route path="/destinos" element={<DestinosPage />} />
                    <Route path="/destinoinfo/:id" element={<DestinoDetallePage />} />
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
                    <Route path="/administracion/caracteristicas" element={<AdministracionCaracteristicasPage />} />
                    <Route path="/administracion/caracteristicas/editar/:id" element={<AdministracionEditarCaracteristicasPage />} />
                    <Route path="/administracion/caracteristicas/crear" element={<AdministracionGuardarCaracteristicasPage />} />
                    <Route path="/administracion/usuarios" element={<AdministracionUserPage />} />
                    <Route path="/registro" element={<RegistrarUsuarioPage />} />
                    <Route path="/login" element={<IniciarSesionUsuarioPage />} />
                    <Route path="/perfil" element={<PerfilUsuarioPage />} />
                    <Route path="/*" element={<VuelosPage />} />
                  </Routes>
                  <WhatsappComponent/>
                  <FooterComponent />
                </CaracteristicasProvider>
              </CategoriasProvider>
            </DestinosProvider>
          </VuelosProvider>
        </MyUserProvider>
      </UsuarioProvider>
    </AuthProvider>

  )
}

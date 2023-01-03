import './styles/LoginForm.css'
import './styles/CertificateCatalog.css'
import './styles/TagCatalog.css'
import './styles/UserCatalog.css'
import './styles/ModelView.css'
import './styles/MainContent.css'
import './styles/Header.css'
import './styles/Footer.css'
import './styles/Error.css'
import './styles/Pagination.css'
import './styles/Basket.css'
import './styles/OrderCatalog.css'
import './styles/UserRoom.css'
import './styles/OrderDetails.css'
import './styles/UserOrderRoom.css'


import LoginForm from './components/LoginForm';
import RegistartionForm from './components/RegistrationForm';
import CertificatreCatalog from './components/catalogs/CertificatreCatalog';
import TagCatalog from './components/catalogs/TagCatalog';
import UserCatalog from './components/catalogs/UserCatalog';
import MainContent from './components/MainContent'
import Header from './components/Header'
import Footer from './components/Footer'
import ErrorPage from './components/Error/ErrorServer'
import ErrorPage404 from './components/Error/Error404'
import Error403 from './components/Error/Error403'
import AdminHeader from './components/AdminHeader'
import Basket from './components/Basket'
import OrderCatalog from './components/catalogs/OrderCatalog'
import OrderDetailsCatalog from './components/catalogs/OrderDetailsCatalog'
import UserRoom from './components/UserRoom';
import UserOrderRoom from './components/UserOrderRoom';


import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'


function App() {

  return (
    <div>
      <Router>

          {(localStorage.getItem("roles") === "[ADMIN]") ?
            (<AdminHeader/>) : (<Header/>)}
        
        <Routes>

          <Route path='/' element={<MainContent/>}/>
          <Route path='*' element={<ErrorPage404/>}/>
          <Route path='basket' element={<Basket/>}/>
          <Route path='error-page-server' element={<ErrorPage/>}/>
          <Route path='error-page-404' element={<ErrorPage404/>}/>
          <Route path='error-page-403' element={<Error403/>}/>

          <Route path='login' element={<LoginForm/>}/>
          <Route path='registration' element={<RegistartionForm/>}/>

          {(localStorage.getItem("roles") === "[ADMIN]") ?
            (<Route path='certificate-catalog' element={<CertificatreCatalog/>}/>) : ("")}

          {(localStorage.getItem("roles") === "[ADMIN]") ? 
            (<Route path='user-catalog' element={<UserCatalog/>}/>) : ("")}

          {(localStorage.getItem("roles") === "[ADMIN]") ?
            (<Route path='tag-catalog' element={<TagCatalog/>}/>) : ("")}

          {(localStorage.getItem("roles") === "[ADMIN]") ?
            (<Route path='order-catalog' element={<OrderCatalog/>}/>) : ("")}

          {(localStorage.getItem("roles") === "[ADMIN]") ?
            (<Route path='order-details-catalog' element={<OrderDetailsCatalog/>}/>) : ("")}

          {(localStorage.getItem("roles") !== null) ?
            (<Route path='user-room' element={<UserRoom/>}/>) : ("")}

          {(localStorage.getItem("roles") !== null) ?
            (<Route path='user-order-room' element={<UserOrderRoom/>}/>) : ("")}

        </Routes> 
        <Footer/>
      </Router>
    </div>
    
  );
}

export default App;

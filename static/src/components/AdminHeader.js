import React from 'react';
import { useNavigate  } from 'react-router-dom';

function AdminHeader() {
    const navigate = useNavigate();

    const Logout = () => {
        localStorage.clear();
        navigate("/");
      }

  return (
    <div className='header'>
        
        <nav className='header-content'>
          <div className='logo'> Store...</div>
            <ui>
                <li onClick={() => navigate("certificate-catalog")}>Certificate</li>
                <li onClick={() => navigate("tag-catalog")}>Tags</li>
                <li onClick={() => navigate("order-catalog")}>Orders</li>
                <li onClick={() => navigate("order-details-catalog")}>Order Details</li>
                <li onClick={() => navigate("user-catalog")}>Users</li>
                <li onClick={() => navigate("user-room")}>My room</li>
            </ui>
      
          <ui className='logout'>
            <li onClick={Logout}>Logout</li>
        </ui>
        </nav>
      </div>
  )
}

export default AdminHeader
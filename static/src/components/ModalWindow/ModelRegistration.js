import React from 'react'
import { useNavigate  } from 'react-router-dom';

function ModelRegistration({closeModal, message}) {
    const navigate = useNavigate();
    const jumpMainPage = (e) => {
        console.log("jumpMainPage", false)
        closeModal(false);
        navigate("/");
    }
  return (
    <div className='form-modal-message'>
        <div className='modalBackground'>
            <div className='modalContainer'>
                <button className='titleCloseBtn' onClick={() => closeModal(false)}>X</button>
                <div className='body-model'>
                    <h1>{message}"</h1>
                </div>
                <div className='footer-model'>
                    <button onClick={(e) => jumpMainPage(false)}>Continue</button>
                </div>
            </div>
        </div>
    </div>
  )
}

export default ModelRegistration
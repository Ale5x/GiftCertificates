import React from 'react';


function InfoDeleteMessage({closeModal, responseStatus, message}) {

    const deleteItem = () => {
        responseStatus(true);
        closeModal(false);
    }

    const cancel = () => {
        responseStatus(false);
        closeModal(false);
    }

  return (
    <div className='form-modal-message'>
        <div className='modalBackground'>
        <div className='modalContainer'>
            <button className='titleCloseBtn' onClick={cancel}>X</button>
            <div className='body-model'>
                <h1>{message}</h1>
            </div>
            <div className='footer-model'>
                <button onClick={deleteItem} className="btn-modal-delete">Delete</button>
                <button onClick={cancel} className='btn-modal-cancel'>Cancel</button>
            </div>
        </div>
    </div>
    </div>
  )
}
export default InfoDeleteMessage
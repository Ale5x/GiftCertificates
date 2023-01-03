import React, {useState, useEffect} from 'react';
import axios from 'axios';
import { useNavigate  } from 'react-router-dom';
import InfoDeleteMessage from './ModalWindow/InfoDeleteMessage';

function UserRoom() {
    const navigate = useNavigate();
    const [fetching, setFetching] = useState(true);
    const [user, setUser] = useState({firstName: "", lastName: "", email: "", role: "", status: ""});
    const [newDataStatus, setNewDataStatus] = useState(false);
    const [newDataCheckStatus, setNewDataCheckStatus] = useState(true);
    const [newUserData, setNewUserData] = useState({firstName: "", lastName: "", email: "", oldPassword: "", newPassword: "", repeatNewPassword: ""});

    const [messageError, setMessageError] = useState("");
    const [messageErrorFields, setMessageErrorFields] = useState({firstName: "", secondName: "", email: "", oldPassword: "", newPassword: "", repeatNewPassword: ""});
    const [modalInfoStatus, setModalInfoStatus] = useState(false);
    const [openModalDelete, setOpenModalDelete] = useState(false);
    const [modalMessage, setModalMessage] = useState("");
    const [responseStatusFromModal, setResponseStatusFromModal] = useState(false);


    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };


    const sendData = () => {
        if(messageError === "") {
            axios.post("http://localhost:8080/store/user/changeData", 
                {
                    firstName: newUserData.firstName, 
                    lastName: newUserData.lastName, 
                    email: newUserData.email,
                    password: newUserData.oldPassword + "/" + newUserData.newPassword
                }, {headers})
            .then(() => {
        
                setNewUserData({firstName: "", lastName: "", email: "", oldPassword: "", newPassword: "", repaetNewPassword: ""});
                setMessageError("");
                setNewDataStatus(false);

            })
            .catch(() => {
                existEmail();
            })
            }
        };

    const existEmail = () => {
        if(newUserData.email !== "") {
            axios.get(`http://localhost:8080/store/user/existUser?email=${newUserData.email}`)
                .then(res => {
                    if(res.status === 200) {
                        setMessageError("Email busy");
                    }
                })
                .catch(() => {
                    setMessageError("Something went wrong.")
                })
        }
    }

    useEffect(() => {
        if(newUserData.newPassword !== newUserData.repeatNewPassword) {
            setMessageError("Passwords dont match!")
        } else {
            setMessageError("");
        }
    })

    const actionCancel = () => {
        setMessageError("");
        setNewDataStatus(false);
        setNewUserData({firstName: "", lastName: "", email: "", oldPassword: "", newPassword: "", repeatNewPassword: ""});
    }

    const actionReset = () => {
        setNewUserData({firstName: "", lastName: "", email: "", oldPassword: "", newPassword: "", repeatNewPassword: ""});
    }

    const actionDeleteAccount = () => {
        setModalMessage("Are you sure you want to delete your account?")
        setOpenModalDelete(true);
    }

    useEffect(() => {
        axios.get("http://localhost:8080/store/user/username", {headers})
        .then(response => {
            setUser({firstName: response.data.firstName, 
                        lastName: response.data.lastName,
                        email: response.data.email,
                        role: response.data.roles, 
                        status: response.data.status});
      
        })
        .catch(error => {
            console.log("ERRORR", error)
            if(error.response.status === 404) {
                navigate("/error-page-404")
            } else if(error.response.status === 403) {
              navigate("/error-page-403")
            } else {
              navigate("/error-page-server")
            }
            })
        .finally(() => {
            setFetching(false)});
    }, [fetching]);

    useEffect(() => {

        if(responseStatusFromModal) {
            axios.get("http://localhost:8080/store/user/removeAccount", {headers})
                .then(response => {
                    console.log(response.status)
                    if(response.status == 200) {
                        localStorage.clear();
                        navigate("/");
                    }
        })
        .catch(error => {
            console.log("ERRORR", error)
            if(error.response.status === 404) {
                navigate("/error-page-404")
            } else if(error.response.status === 403) {
              navigate("/error-page-403")
            } else {
              navigate("/error-page-server")
            }
            })
        .finally(() => {
            setFetching(false)});
        }
    }, [responseStatusFromModal])

    return (
    <div>
        <div>
            <div>
                {openModalDelete && <InfoDeleteMessage closeModal={setOpenModalDelete} responseStatus={setResponseStatusFromModal} message = {modalMessage}/>}
            </div>
            <div className='name-user-page'>
                <h1>User room</h1>
            </div>
            <div className='message-error-user-room'>
                {(messageError !== "") ? (messageError) : ("")}
            </div>
            <div className='private-user-info'>
                <table>
                    <tbody>
                        {(newDataStatus) ? (
                        <tr>
                            <td> Old data</td>
                            <td> New data</td>
                        </tr> 
                            ) : ("")}
                        <tr>
                            <td><b> First name: </b> {user.firstName}</td>
                            {(newDataStatus) ? (
                                <input type="text" placeholder='Enter new name' name='firstName' onChange={e => setNewUserData({...newUserData, firstName: e.target.value})} value={newUserData.firstName}></input>
                            ) : ("")}
                        </tr>
                        <tr>
                            <td><b> Last name: </b> {user.lastName}</td>
                            {(newDataStatus) ? (
                                <input type="text" placeholder='Enter new last name' name='lastName' onChange={e => setNewUserData({...newUserData, lastName: e.target.value})} value={newUserData.lastName}></input>
                            ) : ("")}
                        </tr>
                        <tr>
                            <td><b>Email: </b> {user.email}</td>
                            {(newDataStatus) ? (
                                <input type="email" placeholder='Enter new email' name='email' onChange={e => setNewUserData({...newUserData, email: e.target.value})} value={newUserData.email}></input>
                            ) : ("")}
                        </tr>
                        <tr>
                            {(newDataStatus) ? (
                               <td><b>Enter old password: </b></td> 
                            ) : ("")}
                            {(newDataStatus) ? (
                               <input type="password" placeholder='Enter old password' name='old-password' onChange={e => setNewUserData({...newUserData, oldPassword: e.target.value})} value={newUserData.oldPassword}></input>
                            ) : ("")}
                        </tr>
                        <tr>
                            {(newDataStatus) ? (
                               <td><b>Enter new password: </b></td> 
                            ) : ("")}
                            {(newDataStatus) ? (
                               <input type="password" placeholder='Enter new password' name='new-password' onChange={e => setNewUserData({...newUserData, newPassword: e.target.value})} value={newUserData.newPassword}></input>
                            ) : ("")}
                        </tr>
                        <tr>
                            {(newDataStatus) ? (
                               <td><b>Repeat new password: </b></td> 
                            ) : ("")}
                            {(newDataStatus) ? (
                               <input type="password" placeholder='Repeat new password' name='reapet-new-password' onChange={e => setNewUserData({...newUserData, repeatNewPassword: e.target.value})} value={newUserData.repeatNewPassword}></input>
                            ) : ("")}
                        </tr>
                        <tr>
                            <td><b> Role: </b> {user.role}</td>
                        </tr>
                        <tr>
                            <td><b>Status: </b> {user.status}</td>
                        </tr>
                    </tbody>
                </table>
                    {(newDataStatus) ? (
                        <button onClick={actionCancel} className="btn-user-cancel-change">Cancel</button>
                    ) : (
                        <button onClick={() => setNewDataStatus(true)} className="btn-user-change-data">Change data</button>)}
                    {(newDataStatus) ? (
                        <button onClick={sendData} className="btn-user-send-data">Send</button>
                    ) : ("")}
                    {(newDataStatus) ? (
                        <button onClick={actionReset} className="btn-user-reset-new-data">Reset</button>
                    ) : ("")}
            </div>
            <div>
                <button className='btn-user-delete-account' onClick={actionDeleteAccount}>Delete account</button>
            </div>
        </div>

    </div>
  );
};


export default UserRoom
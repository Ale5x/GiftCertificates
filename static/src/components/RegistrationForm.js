import React, {useEffect, useState} from 'react';
import ModelRegistration from './ModalWindow/ModelRegistration';
import { useNavigate  } from 'react-router-dom';
import axios from 'axios';


function RegistartionForm() {
  const navigate = useNavigate();
  const [user, setUser] = useState({email: "", firstName: "", secondName: "", password: "", repeatPassword: ""});
  const [placeholderStatus, setPlaceholderStatus] = useState({emailStatus: false, firstNameStatus: false,
    secondNameStatus: false, passwordStatus: false, repeatPasswordStatus: false});
  const [error, setError] = useState({errorMessage: "",
                                        emailError: "",
                                        firstNameError: "",
                                        secondNameError: "",
                                        setPasswordError: "",
                                        repeatPasswordError: ""});


  const [formValid, setFormValid] = useState(false);
  const [openModelRegistrationMessage, setOpenModelRegistrationMessage] = useState(false);
  const [message, setMessage] = useState("");

  useEffect( () => {
    if(user.email != "" && user.firstName != "" && user.secondName != "" &&
    user.password != "" && user.repeatPassword != "" && user.password === user.repeatPassword) {
        setFormValid(true);
        setError({errorMessage: ""});
    } 
  }, [error.emailError, error.firstNameError, error.secondNameError, error.passwordError, error.repeatPasswordError])


  const emailHandler = (e) => {
    if(e.target.value !== "") {
        setError({emailError: ""});
        user.email = e.target.value;
    } else {
        user.email = "";
        setError({emailError: "Email cannot be empty"});
    }
  }

  const firstNameHandler = (e) => {
    if(e.target.value !== "") {
        setError({firstNameError: ""});
        user.firstName = e.target.value;
    } else {
        user.firstName = "";
        setError({firstNameError: "First name cannot be empty"});
    }
  }

  const secondNameHandler = (e) => {
    if(e.target.value !== "") {
        setError({secondNameError: ""});
        user.secondName = e.target.value;
    } else {
        user.secondName = "";
        setError({secondNameError: "Second name cannot be empty"});
    }
  }

  const paswwordHandler = (e) => {
    if(e.target.value !== "") {
      setError({passwordError: ""});
      if(e.target.value.length >= 4 && e.target.value.length <= 30) {
        setError({passwordError: ""}); 
        user.password = e.target.value;
      } else { 
        setError({passwordError: " Password length must not be less than 4 characters and greater than 30 characters."});
      }
    } else {
        setError({passwordError: "Password cannot be empty"});
    }
  }

  const repeatPasswordHandler = (e) => {

    if(e.target.value !== "" && user.password === e.target.value) {
      setError({repeatPasswordError: ""});
      user.repeatPassword = e.target.value;
    } else {
        setError({repeatPasswordError: "Passwords do not match"});
        user.repeatPassword = "";
    }
  }

  const bluerHubdler = (e) => {
    switch(e.target.name) {
      case "email":
        placeholderStatus.emailStatus = true;
        break;
      case "first-name":
        placeholderStatus.firstNameStatus = true;
        break;
      case "second-name":
        placeholderStatus.secondNameStatus = true;
        break;
      case "password":
        placeholderStatus.passwordStatus = true;
        break;
      case "repeat-password":
        placeholderStatus.repeatPasswordStatus = true;
        break;
    }
  }

  const submitHandler = e => {
    e.preventDefault();
    if(user.email === "" || user.secondName === "" || user.firstName === "" || user.password === "" || user.repeatPassword === "") {
      setError({errorMessage: "Fields cannot be empty."});
    } else {
      if(user.password === user.repeatPassword && user.email !== "" && user.firstName !== "" && user.lastName !== "") {
        if(!(user.password === user.repeatPassword) && user.password !== "") {
            setFormValid(false);
            setError({errorMessage: "Passwords do not match"});
          } else {
           createUser(); 
          }
      }
    }
  }

const headers = {
  "Access-Control-Allow-Origin": "*",
  'Access-Control-Allow-Credentials':true,
  'Content-Type': 'application/json'
  }

const createUser = () => {
  axios.post("http://localhost:8080/store/user/create", {
      firstName: user.firstName,
      lastName: user.secondName,
      email: user.email,
      password: user.password
    },
      {headers}
    )
    .then(response => {
      if(response.status === 201) {
        setMessage('Registration successful. Please Sign In to continue...');
        setOpenModelRegistrationMessage(true);
      }})
    .catch(error => {
          axios.get(`http://localhost:8080/store/user/existUser?email=${user.email}`)
          .then(res => {
            if(res.status === 200) {
                setError({errorMessage: "Email busy"});
            }
          })
          .catch(er => {
            setError("Something went wrong.")
          })
        })
}

return (
    <form onSubmit={submitHandler} className='form-position'>
      <div>
        {openModelRegistrationMessage && <ModelRegistration closeModal={setOpenModelRegistrationMessage} message = {message}/>}
      </div>
        <div className='position-centr'>
          <div className="form-inner">
            <h2>Registration</h2>
            {(error.errorMessage !== "") ? (<h3 className='error_message'>{error.errorMessage}</h3>) : ("")}
            {(placeholderStatus.emailStatus && error.emailError) && <div className='error_message'>{error.emailError}</div>}
            <div className="form-group">
              <label htmlFor='email'>Email:</label>
              <input onChange={e => emailHandler(e)} onBlur={e => bluerHubdler(e)} type="email" name="email" id='email' placeholder='Enter email' max='120'/>
            </div>
            {(placeholderStatus.firstNameStatus && error.firstNameError) && <div className='error_message'>{error.firstNameError}</div>}
            <div className="form-group">
              <label htmlFor='first-name'>First name:</label>
              <input onChange={e => firstNameHandler(e)}  onBlur={e => bluerHubdler(e)} type="text" name="first-name" id='first-name' placeholder='Enter first name' max='120'/>
            </div>
            {(placeholderStatus.secondNameStatus && error.secondNameError) && <div className='error_message'>{error.secondNameError}</div>}
            <div className="form-group">
              <label htmlFor='second-name'>Second name:</label>
              <input onChange={e => secondNameHandler(e)} onBlur={e => bluerHubdler(e)} type="text" name="second-name" id='second-name' placeholder='Enter second name' max='120'/>
            </div>
            {(placeholderStatus.passwordStatus && error.passwordError) && <div className='error_message'>{error.passwordError}</div>}
            <div className="form-group">
              <label htmlFor='password'>Password:</label>
              <input onChange={e => paswwordHandler(e)} onBlur={e => bluerHubdler(e)} type="password" name="password" id='password' placeholder='Enter password' max='120'/>
            </div>
            <div className="form-group">
            {(placeholderStatus.repeatPasswordStatus && error.repeatPasswordError) && <div className='error_message'>{error.repeatPasswordError}</div>}
              <label htmlFor='repeat-password'>Repeat Password:</label>
              <input onChange={e => repeatPasswordHandler(e)} onBlur={e => bluerHubdler(e)} type="password" name="repeat-password" id='repeat-password' placeholder='Repeat password' max='120'/>
            </div>
            <div className='position-btn'>
              <button disabled={!formValid} type='submit' className='btn-login'>REGISTARTION</button>
              <button onClick={() => navigate("/")} type='submit' className='btn-cancel'>CANCEL</button>
            </div>
          </div>
        </div>
    </form>
  );
};

export default RegistartionForm
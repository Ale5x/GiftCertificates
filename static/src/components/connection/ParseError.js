import { useNavigate  } from 'react-router-dom';

export function parseStatus(responseError) {
    //const navigate = useNavigate();
    console.log("STATUS ERROR", responseError.response)
    if(responseError.response.status === 404) {
     //   navigate("/error-page-404")
    } else {
     //   navigate("/error-page-server")
    }
}

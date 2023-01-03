import React, {useState, useEffect} from 'react'
import { axiosGet } from './connection/Host';
import {parseStatus} from './connection/ParseError'

function Basket() {
    const [dataFromLocalStorage, setDataFromLocalStorage] = useState([JSON.parse(localStorage.getItem("basket"))]);
    const [goods, setGoods] = useState(dataFromLocalStorage[0]);
    const [certificates, setCertificates] = useState([]);
    const [fetching, setFetching] = useState(true);
    const [loadStatus, setloadStatus] = useState(true);

    const [arr, setArr] = useState ([]);

    useEffect(() => {
      if(goods.lenght !== null) {
        setCertificates([])
      // localStorage.removeItem("basket")
      // console.log("USE-Effect goods", goods)
      // console.log("JSON", localStorage.getItem("basket"))
      if(loadStatus) {
        console.log("Start adding --> ")
          setArr([...arr, "foo"])
          console.log("test --> ", arr)


        // goods.map(id => {
        //   console.log("SET ID", id)
        //   sett([...id]);
        //   console.log("test --> ", test)
        // })
        
      //   goods.map(item => 
      //     axiosGet(`http://localhost:8080/store/certificate/get?id=${item}`)
      //     .then(response => {
      //       // console.log("GETTING ITEM ID ", item)
      //     setCertificates([certificates, response.data]);
      //     // console.log("CERTIFICATE", response.data.giftCertificateDtoId)
      //     console.log("CERTIFICATES", certificates)
      //     })
      //     .catch(error => {
      //       console.log("ERROR IN BASKT", error)
      //       parseStatus(error)
      //     })
      //     .finally(() => {
      //       setFetching(false)
      //     })
      //     )
      }
      }
      setloadStatus(false)
    }, [fetching])
    // console.log("JSON.parse(localStorage.getItem(", JSON.parse(localStorage.getItem("basket")))
    // console.log("Basket lengh", goods !== null)

    const removeCertificate = (e) => {
      console.log("Cert", certificates)
      console.log("removeCertificate", e.target.value)
      // const updateCertificates = certificates.filter(id => {
      //   console.log("ID ---> ", id.giftCertificateDtoId)
      //   console.log("id.giftCertificateDtoId != e.target.alt ---> ", id.giftCertificateDtoId !== e.target.alt)
      //   return id.giftCertificateDtoId !== e.target.alt;
      // })


      const updateCertificates = goods.filter(id => {
        console.log("ID ---> ", id)
        console.log("id.giftCertificateDtoId != e.target.alt ---> ", id !== e.target.value)
        return id !== e.target.alvaluet;
      })

console.log("up", updateCertificates)

      // console.log("updateCertificates", certificates)
      // setCertificates([updateCertificates])
      // setCertificates([])
      localStorage.setItem("basket", JSON.stringify(updateCertificates));
      // setGoods([]);
      setFetching(true)


    }
    
  return (
    <div>
        <h1>This is The Basket page</h1>
        <div className='basket-content'>
            {(certificates !== null) ? (
              certificates.map(item => 
                <div key={item.giftCertificateDtoId}>
                  <h2>{item.name}</h2>
                  <div className='basket-position-price'>
                    <label><b>Price ${item.price}</b></label>
                    <label><button className='btn-delete-item-from-basket' onClick={removeCertificate} value={item.giftCertificateDtoId}>Remove</button></label>
                  </div>
                  <hr></hr>
                </div>
                )
            ) : ("")}
            <button className='btn-pay'>Pay</button>
        </div>
    </div>
  )
}

export default Basket
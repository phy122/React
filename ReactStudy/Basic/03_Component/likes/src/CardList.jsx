import React, { useState, useEffect } from "react";
import Card from "./Card"; // 카드 컴포넌트

const CardList = () => {
//   [
//     { 
//         "no" : 1,
//         "title": "아메리카노",
//         "content": "콜롬비아 원두로 만든...",
//         "likes": 100,
//         "img": "https://i.imgur.com/D1ic2Xk.jpg"
//     },
//     { 
//         "no" : 2,
//         "title": "카페라떼",
//         "content": "라떼는 말이야...",
//         "likes": 200,
//         "img": "https://i.imgur.com/hAzIEVv.jpg"
//     },
//     { 
//         "no" : 3,
//         "title": "쿠키프라페",
//         "content": "오레오 다햇네...",
//         "likes": 300,
//         "img": "https://i.imgur.com/kDhIhLv.jpg"
//     }
// ]

  const [productList, setProductList] = useState([]);

  // 데이터 요청 함수
  // const getProductList =  () => {
  //   const response = fetch("http://localhost:8080/products")
  //   .then(response => {
  //     // 서버 응답을 JSON 형식으로 파싱
  //     return response.json();
  //   })
  //   .then(data => {
  //     // 파싱된 데이터 출력
  //     console.log(data);
  //     // 상태 업데이트
  //     setProductList(data);
  //   })
  //   .catch(error => {
  //     console.log('Request failed',error);
  //   });
  // }

  const getProductList = async () => {
    const response = await fetch("http://localhost:8080/products")
    const productList = await response.json();
    setProductList(productList);
  }
  useEffect(() => {
    getProductList();
  },[]);

  return (
    <div>
      <h1>상품 목록</h1>
      <div style={{
        display: "grid",
        gridTemplateColumns: "repeat(3, auto)",
        rowGap: "30px"
      }}>
        {
          productList.map( (card, index) => {
            return <Card key={card.no} card={ card } />
          })
        }
      </div>
    </div>
  )
}

export default CardList


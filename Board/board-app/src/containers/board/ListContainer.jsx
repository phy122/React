import React, { useEffect, useState } from 'react'
import BoardList from '../../components/board/BoardList'
import * as boards from '../../apis/boards'

const ListContainer = () => {

   // boardList state 업데이트
   // state
   const [boardList, setBoardList] = useState([])

  // 게시글 목록 데이터
  const getList = async () => {
    const response = await boards.list()
    const data = await response.data
    const list = data.list
    const pagination = data.pagination
    console.dir(data)
    console.dir(data.list)
    console.dir(data.pagination)

    setBoardList(list)
  }


  useEffect(() => {
    getList()
  }, [])

  return (
    <>
      <div>ListContainer</div>
      <BoardList boardList={boardList}/>
    </>
  )
}

export default ListContainer
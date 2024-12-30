import { default as React, useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import * as boards from '../../apis/boards'
import BoardUpdateFrom from '../../components/board/BoardUpdateForm'

const UpdateContainer = () => {

  const {id} = useParams()
  const navigate = useNavigate()
  
  // state
  const [board, setBoard] = useState({})

  // 게시글 데이터 요청
  const getBoard = async () => {
    const response = await boards.select(id)
    const data = await response.data
    setBoard(data)
  }

  // 게시글 수정 요청 이벤트 핸들러
  const onUpdate = async (id, title, writer, content) => {
    try {
      const response = await boards.update(id, title, writer, content)
      const data = await response.data
      console.log(data);

      alert('수정 완료')
      // 게시글 목록으로 이동
      navigate('/boards')

    } catch (error) {
      console.log(error);
    }
  }

  // 게시글 삭제 요청 이벤트 핸들러
  const onDelete = async (id) => {
    try {
      const response = await boards.remove(id);
      const data = await response.data
      console.log(data);

      alert('삭제 완료')
      // 게시글 목록으로 이동
      navigate('/boards')

    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    getBoard()
  }, [])

  return (
    <>
      <div>UpdateContainer</div>
      <BoardUpdateFrom board={board}
                       onUpdate={onUpdate}
                       onDelete={onDelete}/>
    </>
  )
}

export default UpdateContainer
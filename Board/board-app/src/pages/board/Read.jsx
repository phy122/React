import React from 'react'
import ReadContainer from '../../containers/board/ReadContainer'
import {useParams} from 'react-router-dom'

const Read = () => {

  const {id} = useParams()
  return (
    <>
    <h5>{id}</h5>
      <ReadContainer/>
    </>
  )
}

export default Read
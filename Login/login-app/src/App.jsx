import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Login from './pages/Login'
import Join from './pages/Join'
import User from './pages/User'
import About from './pages/About'
import Home from './pages/Home'
import LoginContextProvider from './context/LoginContextProvider'

function App() {

  return (
    <BrowserRouter>
      <LoginContextProvider>
        <Routes>
          <Route path="/" element={<Home/>}></Route>  
          <Route path="/login" element={<Login/>}></Route>  
          <Route path="/join" element={<Join/>}></Route>  
          <Route path="/user" element={<User/>}></Route>  
          <Route path="/about" element={<About/>}></Route>  
        </Routes>
      </LoginContextProvider>
    </BrowserRouter>
  )
}

export default App

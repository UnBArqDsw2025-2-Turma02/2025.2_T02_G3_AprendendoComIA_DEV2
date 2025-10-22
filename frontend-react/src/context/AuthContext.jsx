import { createContext, useContext, useState, useEffect } from 'react'
import axios from 'axios'

const AuthContext = createContext()

export function useAuth() {
  return useContext(AuthContext)
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  // Configure axios defaults
  useEffect(() => {
    // Set up axios interceptor to include credentials in all requests
    axios.defaults.withCredentials = true
    
    // Add request interceptor to include auth headers
    axios.interceptors.request.use(
      (config) => {
        // Add any auth headers if needed
        return config
      },
      (error) => {
        return Promise.reject(error)
      }
    )

    // Add response interceptor to handle auth errors
    axios.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response?.status === 401) {
          setUser(null)
        }
        return Promise.reject(error)
      }
    )
  }, [])

  useEffect(() => {
    checkAuth()
  }, [])

  const checkAuth = async () => {
    try {
      const response = await axios.get('/api/auth/me', { withCredentials: true })
      setUser(response.data.user)
    } catch (error) {
      setUser(null)
    } finally {
      setLoading(false)
    }
  }

  const login = async (email, password) => {
    const response = await axios.post('/api/auth/login', { email, password }, { withCredentials: true })
    setUser(response.data.user)
    return response.data
  }

  const register = async (email, name, password, cefrLevel = 'A2') => {
    const response = await axios.post('/api/auth/register', 
      { email, name, password, cefrLevel }, 
      { withCredentials: true }
    )
    setUser(response.data.user)
    return response.data
  }

  const logout = async () => {
    try {
      await axios.post('/api/auth/logout', {}, { withCredentials: true })
    } catch (error) {
      console.log('Logout error:', error)
    } finally {
      setUser(null)
    }
  }

  const updateUser = (updatedUser) => {
    setUser(prevUser => ({ ...prevUser, ...updatedUser }))
  }

  const value = {
    user,
    loading,
    login,
    register,
    logout,
    checkAuth,
    updateUser
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}


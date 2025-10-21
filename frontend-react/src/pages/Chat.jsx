import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { Send, Bot, User as UserIcon, Loader } from 'lucide-react'

export default function Chat() {
  const { user } = useAuth()
  const [messages, setMessages] = useState([])
  const [inputText, setInputText] = useState('')
  const [loading, setLoading] = useState(false)

  const sendMessage = async (e) => {
    e.preventDefault()
    if (!inputText.trim() || loading) return

    const userMessage = {
      role: 'user',
      text: inputText,
      timestamp: new Date()
    }

    setMessages(prev => [...prev, userMessage])
    setInputText('')
    setLoading(true)

    try {
      const response = await axios.post('/api/tutor', {
        userText: inputText,
        userLevel: user.cefrLevel,
        mode: 'conversation'
      }, { withCredentials: true })

      const tutorMessage = {
        role: 'tutor',
        text: response.data.reply,
        corrections: response.data.corrections,
        exercise: response.data.miniExercise,
        timestamp: new Date()
      }

      setMessages(prev => [...prev, tutorMessage])
    } catch (error) {
      console.error('Error sending message:', error)
      const errorMessage = {
        role: 'tutor',
        text: 'Desculpe, ocorreu um erro. Por favor, tente novamente.',
        timestamp: new Date()
      }
      setMessages(prev => [...prev, errorMessage])
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-50 flex flex-col">
      <Navbar />
      
      <main className="flex-1 max-w-4xl mx-auto w-full px-4 py-8 flex flex-col">
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-gray-900">Tutor IA</h1>
          <p className="text-gray-600 mt-1">Pratique inglês com correções em tempo real</p>
        </div>

        {/* Messages */}
        <div className="flex-1 card mb-4 overflow-y-auto max-h-[500px]">
          {messages.length === 0 ? (
            <div className="flex flex-col items-center justify-center h-full text-center text-gray-500">
              <Bot size={48} className="mb-4" />
              <p className="text-lg font-medium">Comece uma conversa!</p>
              <p className="text-sm">Escreva algo em inglês e eu vou te ajudar a melhorar.</p>
            </div>
          ) : (
            <div className="space-y-4">
              {messages.map((msg, idx) => (
                <div key={idx} className={`flex gap-3 ${msg.role === 'user' ? 'justify-end' : ''}`}>
                  {msg.role === 'tutor' && (
                    <div className="w-8 h-8 bg-primary-100 rounded-full flex items-center justify-center flex-shrink-0">
                      <Bot size={20} className="text-primary-600" />
                    </div>
                  )}
                  
                  <div className={`max-w-[70%] ${msg.role === 'user' ? 'order-first' : ''}`}>
                    <div className={`rounded-lg p-4 ${
                      msg.role === 'user' 
                        ? 'bg-primary-600 text-white' 
                        : 'bg-gray-100 text-gray-900'
                    }`}>
                      <p className="whitespace-pre-wrap">{msg.text}</p>
                    </div>

                    {msg.corrections && msg.corrections.length > 0 && (
                      <div className="mt-2 space-y-2">
                        {msg.corrections.map((corr, i) => (
                          <div key={i} className="bg-yellow-50 border border-yellow-200 rounded-lg p-3 text-sm">
                            <p className="text-red-600 line-through">{corr.original}</p>
                            <p className="text-green-600 font-medium">{corr.corrected}</p>
                            <p className="text-gray-700 mt-1">{corr.explanation}</p>
                            {corr.rule && <p className="text-xs text-gray-500 mt-1">Regra: {corr.rule}</p>}
                          </div>
                        ))}
                      </div>
                    )}

                    {msg.exercise && (
                      <div className="mt-2 bg-blue-50 border border-blue-200 rounded-lg p-3">
                        <p className="font-medium text-sm mb-2">{msg.exercise.question}</p>
                        <div className="space-y-1">
                          {msg.exercise.options.map((opt, i) => (
                            <div key={i} className="text-sm">
                              {i === msg.exercise.correct ? '✅ ' : '○ '}{opt}
                            </div>
                          ))}
                        </div>
                        <p className="text-xs text-gray-600 mt-2">{msg.exercise.explanation}</p>
                      </div>
                    )}
                  </div>

                  {msg.role === 'user' && (
                    <div className="w-8 h-8 bg-gray-300 rounded-full flex items-center justify-center flex-shrink-0">
                      <UserIcon size={20} className="text-gray-600" />
                    </div>
                  )}
                </div>
              ))}

              {loading && (
                <div className="flex gap-3">
                  <div className="w-8 h-8 bg-primary-100 rounded-full flex items-center justify-center">
                    <Bot size={20} className="text-primary-600" />
                  </div>
                  <div className="bg-gray-100 rounded-lg p-4">
                    <Loader className="animate-spin text-gray-600" size={20} />
                  </div>
                </div>
              )}
            </div>
          )}
        </div>

        {/* Input */}
        <form onSubmit={sendMessage} className="flex gap-2">
          <input
            type="text"
            value={inputText}
            onChange={(e) => setInputText(e.target.value)}
            placeholder="Type your message in English..."
            className="flex-1 input-field"
            disabled={loading}
          />
          <button 
            type="submit" 
            className="btn-primary"
            disabled={loading || !inputText.trim()}
          >
            <Send size={20} />
          </button>
        </form>
      </main>
    </div>
  )
}


import { useState, useEffect } from 'react'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { Check, X, BookOpen } from 'lucide-react'

export default function Tasks() {
  const [questions, setQuestions] = useState([])
  const [currentIndex, setCurrentIndex] = useState(0)
  const [selectedAnswer, setSelectedAnswer] = useState(null)
  const [showResult, setShowResult] = useState(false)
  const [score, setScore] = useState(0)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadQuestions()
  }, [])

  const loadQuestions = async () => {
    try {
      const response = await axios.get('/api/tasks/quiz', { withCredentials: true })
      setQuestions(response.data.questions)
    } catch (error) {
      console.error('Error loading questions:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleAnswer = (optionIndex) => {
    if (showResult) return
    
    setSelectedAnswer(optionIndex)
    setShowResult(true)

    if (optionIndex === questions[currentIndex].answerIndex) {
      setScore(score + 1)
    }
  }

  const nextQuestion = () => {
    if (currentIndex < questions.length - 1) {
      setCurrentIndex(currentIndex + 1)
      setSelectedAnswer(null)
      setShowResult(false)
    }
  }

  const restart = () => {
    setCurrentIndex(0)
    setSelectedAnswer(null)
    setShowResult(false)
    setScore(0)
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="flex items-center justify-center h-96">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
        </div>
      </div>
    )
  }

  const currentQuestion = questions[currentIndex]
  const isFinished = currentIndex === questions.length - 1 && showResult

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      
      <main className="max-w-3xl mx-auto px-4 py-8">
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-gray-900">Exercícios de Gramática</h1>
          <p className="text-gray-600 mt-1">Teste seus conhecimentos com quizzes</p>
        </div>

        {!isFinished ? (
          <div>
            <div className="mb-4 flex justify-between items-center">
              <span className="text-sm text-gray-600">
                Questão {currentIndex + 1} de {questions.length}
              </span>
              <span className="text-sm font-medium text-primary-600">
                Pontuação: {score} / {questions.length}
              </span>
            </div>

            <div className="card mb-6">
              <div className="mb-6">
                <BookOpen size={24} className="text-primary-600 mb-3" />
                <h2 className="text-xl font-semibold text-gray-900">
                  {currentQuestion?.prompt}
                </h2>
              </div>

              <div className="space-y-3">
                {currentQuestion?.options.map((option, idx) => {
                  const isSelected = selectedAnswer === idx
                  const isCorrect = idx === currentQuestion.answerIndex
                  const showCorrect = showResult && isCorrect
                  const showWrong = showResult && isSelected && !isCorrect

                  return (
                    <button
                      key={idx}
                      onClick={() => handleAnswer(idx)}
                      disabled={showResult}
                      className={`w-full text-left p-4 rounded-lg border-2 transition-all ${
                        showCorrect
                          ? 'border-green-500 bg-green-50'
                          : showWrong
                          ? 'border-red-500 bg-red-50'
                          : isSelected
                          ? 'border-primary-500 bg-primary-50'
                          : 'border-gray-200 hover:border-primary-300 bg-white'
                      } ${showResult ? 'cursor-not-allowed' : 'cursor-pointer'}`}
                    >
                      <div className="flex items-center justify-between">
                        <span className="font-medium">{option}</span>
                        {showCorrect && <Check className="text-green-600" size={20} />}
                        {showWrong && <X className="text-red-600" size={20} />}
                      </div>
                    </button>
                  )
                })}
              </div>

              {showResult && (
                <div className={`mt-6 p-4 rounded-lg ${
                  selectedAnswer === currentQuestion.answerIndex
                    ? 'bg-green-50 border border-green-200'
                    : 'bg-red-50 border border-red-200'
                }`}>
                  <p className={`font-medium mb-2 ${
                    selectedAnswer === currentQuestion.answerIndex
                      ? 'text-green-800'
                      : 'text-red-800'
                  }`}>
                    {selectedAnswer === currentQuestion.answerIndex
                      ? '✅ Correto!'
                      : '❌ Incorreto'}
                  </p>
                  <p className="text-sm text-gray-700">
                    {currentQuestion.explanation}
                  </p>
                </div>
              )}
            </div>

            {showResult && currentIndex < questions.length - 1 && (
              <button onClick={nextQuestion} className="w-full btn-primary">
                Próxima Questão
              </button>
            )}
          </div>
        ) : (
          <div className="card text-center py-12">
            <div className="text-6xl mb-4">
              {score === questions.length ? '🎉' : score > questions.length / 2 ? '👍' : '📚'}
            </div>
            <h2 className="text-2xl font-bold text-gray-900 mb-2">
              Quiz Concluído!
            </h2>
            <p className="text-lg text-gray-600 mb-6">
              Você acertou <span className="font-bold text-primary-600">{score}</span> de {questions.length} questões
            </p>
            <div className="mb-6">
              <div className="inline-block px-6 py-2 bg-primary-100 rounded-full">
                <span className="text-2xl font-bold text-primary-700">
                  {Math.round((score / questions.length) * 100)}%
                </span>
              </div>
            </div>
            <button onClick={restart} className="btn-primary">
              Fazer Novamente
            </button>
          </div>
        )}
      </main>
    </div>
  )
}


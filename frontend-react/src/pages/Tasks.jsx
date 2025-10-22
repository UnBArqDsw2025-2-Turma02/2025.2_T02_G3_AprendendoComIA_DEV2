import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { Check, X, BookOpen, Zap, Star, Flame, Trophy, Target, Clock, Sparkles, ArrowRight, RotateCcw } from 'lucide-react'

export default function Tasks() {
  const { user } = useAuth()
  const [questions, setQuestions] = useState([])
  const [currentIndex, setCurrentIndex] = useState(0)
  const [selectedAnswer, setSelectedAnswer] = useState(null)
  const [showResult, setShowResult] = useState(false)
  const [score, setScore] = useState(0)
  const [loading, setLoading] = useState(true)
  const [xp, setXp] = useState(0)
  const [streak, setStreak] = useState(0)

  // Mock questions for demonstration
  const mockQuestions = [
    {
      id: 1,
      prompt: "Choose the correct form: 'I _____ to the store yesterday.'",
      options: ["go", "went", "gone", "going"],
      answerIndex: 1,
      explanation: "The past tense of 'go' is 'went'. We use 'went' for actions that happened in the past.",
      type: "grammar",
      difficulty: "A2"
    },
    {
      id: 2,
      prompt: "What is the plural of 'child'?",
      options: ["childs", "children", "childes", "child"],
      answerIndex: 1,
      explanation: "The plural of 'child' is 'children'. This is an irregular plural form.",
      type: "vocabulary",
      difficulty: "A1"
    },
    {
      id: 3,
      prompt: "Complete: 'She is _____ than her sister.'",
      options: ["tall", "taller", "tallest", "more tall"],
      answerIndex: 1,
      explanation: "We use 'taller' for comparative adjectives with short adjectives. 'More tall' is incorrect.",
      type: "grammar",
      difficulty: "A2"
    },
    {
      id: 4,
      prompt: "Which sentence is correct?",
      options: [
        "I have been living here for 5 years.",
        "I am living here for 5 years.",
        "I live here for 5 years.",
        "I lived here for 5 years."
      ],
      answerIndex: 0,
      explanation: "We use present perfect continuous (have been living) for actions that started in the past and continue to the present.",
      type: "grammar",
      difficulty: "B1"
    },
    {
      id: 5,
      prompt: "What does 'procrastinate' mean?",
      options: ["To work hard", "To delay or postpone", "To celebrate", "To communicate"],
      answerIndex: 1,
      explanation: "To procrastinate means to delay or postpone doing something, usually something important.",
      type: "vocabulary",
      difficulty: "B2"
    }
  ]

  useEffect(() => {
    loadQuestions()
  }, [])

  const loadQuestions = async () => {
    try {
      // Load exercises by user's CEFR level
      const cefrLevel = user.cefrLevel || 'A1'
      const response = await axios.get(`/api/exercises/random/${cefrLevel}/5`, {
        withCredentials: true
      })
      
      // Transform exercises to questions format
      const exercises = response.data
      const questions = exercises.map(exercise => ({
        id: exercise.id,
        title: exercise.title,
        type: exercise.type,
        difficulty: exercise.difficulty,
        cefrLevel: exercise.cefrLevel,
        xpReward: exercise.xpReward,
        questions: exercise.questions || []
      }))
      
      setQuestions(questions)
    } catch (error) {
      console.error('Error loading questions:', error)
      // Fallback to mock data if API fails
      setQuestions(mockQuestions)
    } finally {
      setLoading(false)
    }
  }

  const handleAnswer = async (optionIndex) => {
    if (showResult) return
    
    setSelectedAnswer(optionIndex)
    setShowResult(true)

    try {
      const currentExercise = questions[currentIndex]
      if (!currentExercise || !currentExercise.questions) {
        console.error('Current exercise or questions not found')
        return
      }
      
      const answers = currentExercise.questions.map((question, qIndex) => ({
        questionId: question.id,
        answer: optionIndex.toString() // Simplified for now
      }))

      // Submit exercise attempt to backend
      const response = await axios.post(`/api/exercises/${currentExercise.id}/attempt`, {
        userId: user.id,
        answers: answers,
        timeSpent: 30
      }, { withCredentials: true })

      // Update XP from response
      if (response.data.xpEarned) {
        setXp(prev => prev + response.data.xpEarned)
      }

      // Update score
      if (response.data.correctAnswers) {
        setScore(score + response.data.correctAnswers)
      }
    } catch (error) {
      console.error('Error submitting answer:', error)
      // Still allow progression even if API fails
      if (optionIndex === 0) { // Assume first option is correct for fallback
        setScore(score + 1)
        setXp(prev => prev + 20)
      } else {
        setXp(prev => prev + 5)
      }
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
    setXp(0)
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
        <Navbar />
        <div className="flex items-center justify-center h-96">
          <div className="animate-spin rounded-full h-16 w-16 border-4 border-green-500 border-t-transparent"></div>
        </div>
      </div>
    )
  }

  const currentQuestion = questions[currentIndex]
  const isFinished = currentIndex === questions.length - 1 && showResult
  const progressPercentage = ((currentIndex + (showResult ? 1 : 0)) / questions.length) * 100

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
      <Navbar />
      
      <main className="max-w-4xl mx-auto px-4 py-8">
        {/* Header */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-4xl font-bold text-gray-900 mb-2">Exercícios</h1>
              <p className="text-xl text-gray-600">Teste seus conhecimentos com quizzes interativos</p>
            </div>
            
            {/* Stats */}
            <div className="flex items-center gap-4">
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Zap className="text-blue-500" size={20} />
                <span className="font-bold text-lg">{xp} XP</span>
              </div>
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Flame className="streak-fire" size={20} />
                <span className="font-bold text-lg">{streak}</span>
              </div>
            </div>
          </div>

          {/* Progress */}
          {!isFinished && (
            <div className="mb-6">
              <div className="flex justify-between text-sm text-gray-600 mb-2">
                <span>Progresso</span>
                <span>{currentIndex + (showResult ? 1 : 0)} / {questions.length}</span>
              </div>
              <div className="progress-bar">
                <div 
                  className="progress-fill" 
                  style={{ width: `${progressPercentage}%` }}
                ></div>
              </div>
            </div>
          )}
        </div>

        {!isFinished ? (
          <div>
            {/* Question Card */}
            <div className="card mb-8">
              <div className="flex items-center justify-between mb-6">
                <div className="flex items-center gap-3">
                  <div className="w-12 h-12 bg-gradient-to-r from-green-500 to-emerald-600 rounded-2xl flex items-center justify-center">
                    <BookOpen className="text-white" size={24} />
                  </div>
                  <div>
                    <h2 className="text-xl font-bold text-gray-900">Questão {currentIndex + 1}</h2>
                    <div className="flex items-center gap-2 text-sm text-gray-600">
                      <span className="level-badge text-xs px-2 py-1">{currentQuestion?.difficulty}</span>
                      <span className="capitalize">{currentQuestion?.type}</span>
                    </div>
                  </div>
                </div>
                <div className="text-right">
                  <div className="text-2xl font-bold text-green-600">{score}</div>
                  <div className="text-sm text-gray-500">acertos</div>
                </div>
              </div>

              <div className="mb-8">
                <h3 className="text-2xl font-semibold text-gray-900 mb-6">
                  {currentQuestion?.prompt}
                </h3>

                <div className="space-y-4">
                  {currentQuestion?.options?.map((option, idx) => {
                    const isSelected = selectedAnswer === idx
                    const isCorrect = idx === currentQuestion.answerIndex
                    const showCorrect = showResult && isCorrect
                    const showWrong = showResult && isSelected && !isCorrect

                    return (
                      <button
                        key={idx}
                        onClick={() => handleAnswer(idx)}
                        disabled={showResult}
                        className={`w-full text-left p-6 rounded-2xl border-2 transition-all duration-200 ${
                          showCorrect
                            ? 'border-green-500 bg-green-50 text-green-800'
                            : showWrong
                            ? 'border-red-500 bg-red-50 text-red-800'
                            : isSelected
                            ? 'border-green-300 bg-green-50 text-green-700'
                            : 'border-gray-200 hover:border-green-300 hover:bg-green-50 bg-white text-gray-900'
                        } ${showResult ? 'cursor-not-allowed' : 'cursor-pointer hover:shadow-md'}`}
                      >
                        <div className="flex items-center justify-between">
                          <span className="text-lg font-medium">{option}</span>
                          {showCorrect && <Check className="text-green-600" size={24} />}
                          {showWrong && <X className="text-red-600" size={24} />}
                        </div>
                      </button>
                    )
                  })}
                </div>
              </div>

              {showResult && (
                <div className={`p-6 rounded-2xl border-2 ${
                  selectedAnswer === currentQuestion.answerIndex
                    ? 'bg-green-50 border-green-200'
                    : 'bg-red-50 border-red-200'
                }`}>
                  <div className="flex items-center gap-3 mb-4">
                    {selectedAnswer === currentQuestion.answerIndex ? (
                      <div className="w-8 h-8 bg-green-500 rounded-full flex items-center justify-center">
                        <Check className="text-white" size={20} />
                      </div>
                    ) : (
                      <div className="w-8 h-8 bg-red-500 rounded-full flex items-center justify-center">
                        <X className="text-white" size={20} />
                      </div>
                    )}
                    <h4 className={`text-lg font-bold ${
                      selectedAnswer === currentQuestion.answerIndex
                        ? 'text-green-800'
                        : 'text-red-800'
                    }`}>
                      {selectedAnswer === currentQuestion.answerIndex
                        ? 'Correto! 🎉'
                        : 'Incorreto 😔'}
                    </h4>
                  </div>
                  <p className="text-gray-700 leading-relaxed">
                    {currentQuestion.explanation}
                  </p>
                  <div className="mt-4 flex items-center gap-2 text-sm text-gray-600">
                    <Sparkles size={16} />
                    <span>
                      {selectedAnswer === currentQuestion.answerIndex ? '+20 XP' : '+5 XP'}
                    </span>
                  </div>
                </div>
              )}
            </div>

            {showResult && currentIndex < questions.length - 1 && (
              <button onClick={nextQuestion} className="w-full btn-primary text-lg py-4 flex items-center justify-center gap-2">
                Próxima Questão <ArrowRight size={20} />
              </button>
            )}
          </div>
        ) : (
          <div className="card text-center py-16">
            <div className="w-24 h-24 bg-gradient-to-r from-green-500 to-emerald-600 rounded-full flex items-center justify-center mx-auto mb-6">
              <Trophy className="text-white" size={40} />
            </div>
            
            <h2 className="text-4xl font-bold text-gray-900 mb-4">
              Quiz Concluído! 🎉
            </h2>
            
            <p className="text-xl text-gray-600 mb-8">
              Você acertou <span className="font-bold text-green-600 text-2xl">{score}</span> de {questions.length} questões
            </p>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
              <div className="bg-green-50 border-2 border-green-200 rounded-2xl p-6">
                <div className="text-3xl font-bold text-green-600 mb-2">
                  {Math.round((score / questions.length) * 100)}%
                </div>
                <div className="text-sm text-gray-600">Precisão</div>
              </div>
              
              <div className="bg-blue-50 border-2 border-blue-200 rounded-2xl p-6">
                <div className="text-3xl font-bold text-blue-600 mb-2">
                  +{xp} XP
                </div>
                <div className="text-sm text-gray-600">Pontos Ganhos</div>
              </div>
              
              <div className="bg-orange-50 border-2 border-orange-200 rounded-2xl p-6">
                <div className="text-3xl font-bold text-orange-600 mb-2">
                  {score > questions.length / 2 ? 'Excelente!' : score > 0 ? 'Bom!' : 'Continue!'}
                </div>
                <div className="text-sm text-gray-600">Performance</div>
              </div>
            </div>

            <div className="flex gap-4 justify-center">
              <button onClick={restart} className="btn-primary flex items-center gap-2">
                <RotateCcw size={20} />
                Fazer Novamente
              </button>
              <button className="btn-secondary">
                Ver Estatísticas
              </button>
            </div>
          </div>
        )}
      </main>
    </div>
  )
}


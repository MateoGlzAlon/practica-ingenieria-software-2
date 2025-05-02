"use client"

import { useState } from "react"
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts"
import { Building, MessageSquare, Award, BookOpen, Users, GitlabIcon as GitHub, Twitter, Globe } from "lucide-react"

// Todos los datos necesarios para la página están en esta variable
const pageData = {
    user: {
        name: "UPDATE-DB-Alice Jackson",
        username: "alice",
        avatar: "https://placehold.co/400x400?text=Post1",
        role: "UPDATE-DB-IF-WANTED-Senior Backend Developer",
        memberSince: "Abril 2021",
        stats: {
            questions: 42,
            answers: 187,
        },
        about:
            "Desarrollador con más de 8 años de experiencia en tecnologías web. Especializado en React, Node.js y arquitecturas cloud. Contribuidor activo en proyectos open source y mentor de desarrolladores junior.",
        links: {
            github: "UPDATE-DB-IF-WANTED-github.com/carlos_dev",
            twitter: "UPDATE-DB-IF-WANTED-twitter.com/carlos_dev",
            website: "UPDATE-DB-IF-WANTED-carlosdev.com",
        },
    },
    activityData: [
        { month: "Ene", contributions: 45 },
        { month: "Feb", contributions: 32 },
        { month: "Mar", contributions: 67 },
        { month: "Abr", contributions: 58 },
        { month: "May", contributions: 43 },
        { month: "Jun", contributions: 89 },
        { month: "Jul", contributions: 76 },
        { month: "Ago", contributions: 91 },
        { month: "Sep", contributions: 65 },
        { month: "Oct", contributions: 83 },
        { month: "Nov", contributions: 72 },
        { month: "Dic", contributions: 56 },
    ],
    posts: [
        {
            id: 1,
            title: "Cómo optimizar el rendimiento de aplicaciones React",
            votes: 124,
            answers: 8,
            created_at: "publicado el 15-3-2023",
        },
        {
            id: 2,
            title: "Guía completa para implementar autenticación con JWT",
            votes: 98,
            answers: 12,
            created_at: "publicado el 22-5-2023",
        },
        {
            id: 3,
            title: "Mejores prácticas para estructurar proyectos Node.js",
            votes: 87,
            answers: 6,
            created_at: "publicado el 7-8-2023",
        },
    ],
}

export default function ProfilePage() {
    const [activeTab, setActiveTab] = useState("perfil")
    const { user, activityData, topPosts } = pageData

    return (
        <div className="min-h-screen bg-gray-50">

            {/* Main Content */}
            <main className="max-w-6xl mx-auto px-4 py-6 grid grid-cols-1 md:grid-cols-4 gap-6">
                {/* Sidebar */}
                <div className="md:col-span-1">
                    <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
                        <div className="p-6 flex flex-col items-center text-center">
                            <img src={user.avatar || "/placeholder.svg"} alt={user.name} className="w-24 h-24 rounded-full object-cover mb-4" />
                            <h1 className="text-xl font-bold">{user.name}</h1>
                            <p className="text-gray-600 mb-2">@{user.username}</p>
                            <p className="text-sm text-gray-500 mb-4">{user.role}</p>



                            <div className="w-full border-t border-gray-200 pt-4">
                                <div className="flex items-center text-sm mb-2">
                                    <MessageSquare className="h-4 w-4 mr-2 text-gray-500" />
                                    <span>Miembro desde {user.memberSince}</span>
                                </div>
                            </div>
                        </div>

                        <div className="border-t border-gray-200 p-4">
                            <h3 className="font-medium mb-3">Enlaces</h3>
                            <div className="space-y-2">
                                <a
                                    href={`https://${user.links.github}`}
                                    className="flex items-center text-sm text-blue-600 hover:underline"
                                >
                                    <GitHub className="h-4 w-4 mr-2" />
                                    {user.links.github}
                                </a>
                                <a
                                    href={`https://${user.links.twitter}`}
                                    className="flex items-center text-sm text-blue-600 hover:underline"
                                >
                                    <Twitter className="h-4 w-4 mr-2" />
                                    {user.links.twitter}
                                </a>
                                <a
                                    href={`https://${user.links.website}`}
                                    className="flex items-center text-sm text-blue-600 hover:underline"
                                >
                                    <Globe className="h-4 w-4 mr-2" />
                                    {user.links.website}
                                </a>
                            </div>
                        </div>

                        <div className="border-t border-gray-200 p-4">
                            <h3 className="font-medium mb-3">Estadísticas</h3>
                            <div className="grid grid-cols-3 gap-2 text-center">
                                <div className="bg-gray-50 p-2 rounded">
                                    <div className="text-lg font-bold">{user.stats.questions}</div>
                                    <div className="text-xs text-gray-500">Preguntas</div>
                                </div>
                                <div className="bg-gray-50 p-2 rounded">
                                    <div className="text-lg font-bold">{user.stats.answers}</div>
                                    <div className="text-xs text-gray-500">Respuestas</div>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>

                {/* Main Content */}
                <div className="md:col-span-3">
                    {/* Tabs */}
                    <div className="bg-white rounded-lg border border-gray-200 mb-6">
                        <div className="border-b border-gray-200">
                            <nav className="flex -mb-px">
                                <button
                                    onClick={() => setActiveTab("perfil")}
                                    className={`px-4 py-3 text-sm font-medium ${activeTab === "perfil"
                                        ? "border-b-2 border-blue-500 text-blue-600"
                                        : "text-gray-500 hover:text-gray-700 hover:border-gray-300"
                                        }`}
                                >
                                    Perfil
                                </button>
                                <button
                                    onClick={() => setActiveTab("actividad")}
                                    className={`px-4 py-3 text-sm font-medium ${activeTab === "actividad"
                                        ? "border-b-2 border-blue-500 text-blue-600"
                                        : "text-gray-500 hover:text-gray-700 hover:border-gray-300"
                                        }`}
                                >
                                    Actividad
                                </button>
                                <button
                                    onClick={() => setActiveTab("respuestas")}
                                    className={`px-4 py-3 text-sm font-medium ${activeTab === "respuestas"
                                        ? "border-b-2 border-blue-500 text-blue-600"
                                        : "text-gray-500 hover:text-gray-700 hover:border-gray-300"
                                        }`}
                                >
                                    Respuestas
                                </button>
                            </nav>
                        </div>

                        {/* Tab Content */}
                        <div className="p-6">
                            {activeTab === "perfil" && (
                                <div>
                                    <h2 className="text-xl font-bold mb-4">Sobre mí</h2>
                                    <p className="text-gray-700 mb-6">{user.about}</p>

                                    <h2 className="text-xl font-bold mb-4">Actividad reciente</h2>
                                    <div className="h-64 w-full">
                                        <ResponsiveContainer width="100%" height="100%">
                                            <BarChart data={activityData}>
                                                <CartesianGrid strokeDasharray="3 3" />
                                                <XAxis dataKey="month" />
                                                <YAxis />
                                                <Tooltip />
                                                <Bar dataKey="contributions" fill="#3b82f6" />
                                            </BarChart>
                                        </ResponsiveContainer>
                                    </div>
                                </div>
                            )}

                            {activeTab === "actividad" && (
                                <div>
                                    <h2 className="text-xl font-bold mb-4">Publicaciones principales</h2>
                                    <div className="space-y-4">
                                        {topPosts.map((post) => (
                                            <div key={post.id} className="border border-gray-200 rounded-lg p-4">
                                                <h3 className="text-lg font-medium text-blue-600 hover:underline cursor-pointer mb-2">
                                                    {post.title}
                                                </h3>
                                                <div className="flex items-center text-sm text-gray-500">
                                                    <span className="flex items-center mr-4">
                                                        <svg className="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 15l7-7 7 7" />
                                                        </svg>
                                                        {post.votes} votos
                                                    </span>
                                                    <span className="flex items-center mr-4">
                                                        <MessageSquare className="h-4 w-4 mr-1" />
                                                        {post.answers} respuestas
                                                    </span>
                                                    <span>{post.created_at}</span>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            )}

                            {activeTab === "respuestas" && (
                                <div>
                                    <h2 className="text-xl font-bold mb-4">Mis respuestas</h2>
                                    <div className="bg-gray-100 rounded-lg p-6 text-center">
                                        <BookOpen className="h-12 w-12 mx-auto text-gray-400 mb-2" />
                                        <p className="text-gray-600">Selecciona esta pestaña para ver todas tus respuestas</p>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </main>
        </div>
    )
}

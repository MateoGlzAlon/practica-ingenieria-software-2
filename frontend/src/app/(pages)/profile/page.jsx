"use client"

import { useState, useEffect } from 'react'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts"
import Link from "next/link";
import { MessageSquare, BookOpen, GitlabIcon as GitHub, Twitter, Globe, Vote, Pencil } from "lucide-react"
import getProfileUser from "@/api/getProfileUser"
import changeLinksProfile from '@/api/changeLinksProfile';
import getCommentsOfAUser from '@/api/getCommentsOfAUser';
import getUserIdFromLocalStorage from '@/hooks/getUserIdAuth';


export default function ProfilePage() {

    const idUser = getUserIdFromLocalStorage();

    const [profileData, setProfileData] = useState(null)
    const [comments, setComments] = useState([]);
    const [activeTab, setActiveTab] = useState('profile')

    const [editing, setEditing] = useState({
        github: false,
        twitter: false,
        website: false,
    });

    const [links, setLinks] = useState(null);


    useEffect(() => {
        if (!idUser) return

        const fetchProfile = async () => {
        try {
            const data = await getProfileUser(idUser)
            setProfileData(data)

            const { user } = data;
            setLinks({
                github: user.githubLink,
                twitter: user.twitterLink,
                website: user.websiteLink,
            });

            //part of comments
            const response = await getCommentsOfAUser(idUser);
            setComments(response);
        } catch (err) {
            console.error('Error fetching profile:', err)
        }

        }

        fetchProfile()
    }, [idUser])


    if (!profileData) {
        return <p className="text-center py-10">Loading profile...</p>
    }

    const { user, activityData, posts } = profileData

    const EditableLinkField = ({ field, Icon, initialValue, onSave }) => {
        const [isEditing, setIsEditing] = useState(false);
        const [value, setValue] = useState(initialValue);

        useEffect(() => {
            setValue(initialValue);
        }, [initialValue]);

        const handleSubmit = async (e) => {
            e.preventDefault();
            await onSave(field, value);
            setIsEditing(false);
        };

        return (
            isEditing ? (
                <form onSubmit={handleSubmit} className="flex items-center gap-2 text-sm text-blue-600 mb-3">
                    <Icon className="h-4 w-4 ml-2" />
                    <input
                        type="text"
                        value={value}
                        onChange={(e) => setValue(e.target.value)}
                        className="border border-gray-300 rounded px-2 py-1 text-sm w-48 text-gray-800"
                        autoFocus
                    />
                    <Pencil
                        className="h-4 w-4 cursor-pointer text-gray-500 hover:text-gray-700"
                        onClick={() => setIsEditing(true)}
                    />
                </form>
            ) : (
                <div className="flex items-center gap-2 text-sm text-blue-600 mb-2">
                    <Icon className="h-4 w-4 ml-2" />
                    <a href={value} className="hover:underline text-gray-700">
                        {value}
                    </a>
                    <Pencil
                        className="h-4 w-4 cursor-pointer text-gray-500 hover:text-gray-700"
                        onClick={() => setIsEditing(true)}
                    />
                </div>
            )
        );
    };


    return (
        <div className="min-h-screen bg-gray-50">

            {/* Main Content */}
            <main className="max-w-6xl mx-auto px-4 py-6 grid grid-cols-1 md:grid-cols-4 gap-6">
                {/* Sidebar */}
                <div className="md:col-span-1">
                    <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
                        <div className="p-6 flex flex-col items-center text-center">
                            <img src={user.avatarUrl || "/placeholder.svg"} alt={user.name} className="w-24 h-24 rounded-full object-cover mb-4" />
                            <h1 className="text-xl font-bold">{user.name}</h1>
                            <p className="text-gray-600 mb-2">@{user.username}</p>
                            <p className="text-sm text-gray-500 mb-4">{user.role}</p>

                            <div className="w-full border-t border-gray-200 pt-4">
                                {/*TO-DO: change this because it is not centred */}
                                <div className="flex items-center text-sm mb-2">
                                    <MessageSquare className="h-4 w-4 mr-2 text-gray-500" />
                                    <span>
                                        Member since{" "}
                                        {new Date(user.memberSince).toLocaleDateString("en-GB", {
                                            day: "2-digit",
                                            month: "short",
                                            year: "numeric",
                                        })}
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div className="space-y-2">
                            <EditableLinkField
                                field="github"
                                Icon={GitHub}
                                initialValue={links?.github}
                                onSave={async (field, newValue) => {
                                    const updatedLinks = {
                                        userId: idUser,
                                        github: field === "github" ? newValue : links.github,
                                        twitter: field === "twitter" ? newValue : links.twitter,
                                        website: field === "website" ? newValue : links.website,
                                    };
                                    await changeLinksProfile(updatedLinks);
                                    setLinks(updatedLinks);
                                }}
                            />
                            <EditableLinkField
                                field="twitter"
                                Icon={Twitter}
                                initialValue={links?.twitter}
                                onSave={async (field, newValue) => {
                                    const updatedLinks = {
                                        userId: idUser,
                                        github: field === "github" ? newValue : links.github,
                                        twitter: field === "twitter" ? newValue : links.twitter,
                                        website: field === "website" ? newValue : links.website,
                                    };
                                    await changeLinksProfile(updatedLinks);
                                    setLinks(updatedLinks);
                                }}
                            />
                            <EditableLinkField
                                field="website"
                                Icon={Globe}
                                initialValue={links?.website}
                                onSave={async (field, newValue) => {
                                    const updatedLinks = {
                                        userId: idUser,
                                        github: field === "github" ? newValue : links.github,
                                        twitter: field === "twitter" ? newValue : links.twitter,
                                        website: field === "website" ? newValue : links.website,
                                    };
                                    await changeLinksProfile(updatedLinks);
                                    setLinks(updatedLinks);
                                }}
                            />
                        </div>

                        <div className="border-t border-gray-200 p-4">
                            <h3 className="font-medium mb-3">Statistics</h3>
                            <div className="grid grid-cols-2 gap-2 text-center">
                                <div className="bg-gray-50 p-2 rounded">
                                    <div className="text-lg font-bold">{user.stats.questions}</div>
                                    <div className="text-xs text-gray-500">Questions</div>
                                </div>
                                <div className="bg-gray-50 p-2 rounded">
                                    <div className="text-lg font-bold">{user.stats.answers}</div>
                                    <div className="text-xs text-gray-500">Answers</div>
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
                                    onClick={() => setActiveTab("profile")}
                                    className={`px-4 py-3 text-sm font-medium ${activeTab === "profile"
                                        ? "border-b-2 border-blue-500 text-blue-600"
                                        : "text-gray-500 hover:text-gray-700 hover:border-gray-300"
                                        }`}
                                >
                                    Profile
                                </button>
                                <button
                                    onClick={() => setActiveTab("activity")}
                                    className={`px-4 py-3 text-sm font-medium ${activeTab === "activity"
                                        ? "border-b-2 border-blue-500 text-blue-600"
                                        : "text-gray-500 hover:text-gray-700 hover:border-gray-300"
                                        }`}
                                >
                                    Activity
                                </button>
                                <button
                                    onClick={() => setActiveTab("answers")}
                                    className={`px-4 py-3 text-sm font-medium ${activeTab === "answers"
                                        ? "border-b-2 border-blue-500 text-blue-600"
                                        : "text-gray-500 hover:text-gray-700 hover:border-gray-300"
                                        }`}
                                >
                                    Answers
                                </button>
                                <button
                                    onClick={() => setActiveTab("tips")}
                                    className={`px-4 py-3 text-sm font-medium ${activeTab === "tips"
                                        ? "border-b-2 border-blue-500 text-blue-600"
                                        : "text-gray-500 hover:text-gray-700 hover:border-gray-300"
                                        }`}
                                >
                                    Tips
                                </button>
                            </nav>
                        </div>

                        {/* Tab Content */}
                        <div className="p-6">
                            {activeTab === "profile" && (
                                <div>
                                    <h2 className="text-xl font-bold mb-4">About me</h2>
                                    <p className="text-gray-700 mb-6">{user.about}</p>

                                    <h2 className="text-xl font-bold mb-4">Recent activity</h2>
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

                            {activeTab === "activity" && (
                                <div>
                                    <h2 className="text-xl font-bold mb-4">Top posts</h2>
                                    <div className="space-y-4">
                                        {posts.map((post) => (
                                            <div key={post.id} className="border border-gray-200 rounded-lg p-4">
                                                <Link href={`/post/${post.id}`}>
                                                    <h3 className="text-lg font-medium text-blue-600 hover:underline cursor-pointer mb-2">
                                                        {post.title}
                                                    </h3>
                                                </Link>
                                                <div className="flex items-center text-sm text-gray-500">
                                                    <span className="flex items-center mr-4">
                                                        <Vote className="h-4 w-4 mr-1" />
                                                        {post.votes} votes
                                                    </span>
                                                    <span className="flex items-center mr-4">
                                                        <MessageSquare className="h-4 w-4 mr-1" />
                                                        {post.answers} answers
                                                    </span>
                                                    <span>{post.created_at}</span>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            )}

                            {activeTab === "answers" && (
                                <div>
                                    <h2 className="text-xl font-bold mb-4">Top comments</h2>
                                    <div className="space-y-4">
                                        {comments.map((comment) => (
                                            <div key={comment.idPost} className="border border-gray-200 rounded-lg p-4">
                                                <Link href={`/post/${comment.idPost}`}>
                                                <h3 className="text-lg font-medium text-blue-600 hover:underline cursor-pointer mb-2">
                                                    {comment.content}
                                                </h3>
                                                </Link>
                                                <div className="flex items-center text-sm text-gray-500">
                                                <span className="flex items-center mr-4">
                                                    <Vote className="h-4 w-4 mr-1" />
                                                    {comment.votes} votes
                                                </span>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            )}

                            {activeTab === "tips" && (
                                <div>
                                    <h2 className="text-xl font-bold mb-4">Tips</h2>

                                    {/* Incoming Payments */}
                                    <div className="mb-6">
                                        <h3 className="text-lg font-semibold mb-2">Incoming Tips</h3>
                                        <ul className="space-y-2">
                                            {user.tipsReceived.map((tip, index) => (
                                                <li key={index} className="bg-green-50 p-4 rounded border border-green-200">
                                                    <p className="text-sm text-gray-700">
                                                        Received <span className="font-medium">${tip.amount}</span> from <span className="font-medium">@{tip.sender}</span> on {tip.date}
                                                    </p>
                                                </li>
                                            ))}
                                        </ul>
                                    </div>

                                    {/* Outgoing Payments */}
                                    <div>
                                        <h3 className="text-lg font-semibold mb-2">Outgoing Tips</h3>
                                        <ul className="space-y-2">
                                            {user.tipsSent.map((tip, index) => (
                                                <li key={index} className="bg-red-50 p-4 rounded border border-red-200">
                                                    <p className="text-sm text-gray-700">
                                                        Sent <span className="font-medium">${tip.amount}</span> to <span className="font-medium">@{tip.receiver}</span> on {tip.date}
                                                    </p>
                                                </li>
                                            ))}
                                        </ul>
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

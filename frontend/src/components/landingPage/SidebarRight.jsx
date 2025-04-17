"use client"

import { TrendingUp, Award, ExternalLink } from "lucide-react"

export default function SidebarRight() {


    const topUsers = [
        { name: "jonSkeet", reputation: 1234567, badges: { gold: 42, silver: 5621, bronze: 9874 } },
        { name: "darin", reputation: 987654, badges: { gold: 31, silver: 4532, bronze: 7654 } },
        { name: "gordon", reputation: 876543, badges: { gold: 28, silver: 3987, bronze: 6543 } },
    ]

    return (
        <div className="w-[20%] hidden lg:flex flex-col gap-4 sticky top-20 self-start">
            <div className="bg-orange-50 border border-orange-200 p-4 rounded-md">
                <h3 className="font-medium mb-2 text-orange-800">Join the DevOverflow Community</h3>
                <p className="text-sm text-gray-700 mb-3">
                    Get unstuck by asking questions, unlock new privileges, and vote on content.
                </p>
                <button className="w-full bg-orange-500 hover:bg-orange-600 text-white py-2 rounded-md text-sm font-medium">
                    Create Account
                </button>
            </div>


            <div className="bg-white p-4 rounded-md shadow-sm border border-gray-200">
                <div className="flex items-center gap-2 mb-3">
                    <Award className="h-4 w-4 text-orange-500" />
                    <h3 className="font-medium">Top Users</h3>
                </div>
                <ul className="space-y-3">
                    {topUsers.map((user, index) => (
                        <li key={index} className="flex justify-between items-center">
                            <div className="text-sm text-blue-700 hover:text-blue-900">
                                {user.name}
                            </div>
                            <div className="flex items-center gap-1 text-xs">
                                <span className="text-amber-500">{user.badges.gold}</span>
                                <span className="text-gray-400">{user.badges.silver}</span>
                                <span className="text-amber-700">{user.badges.bronze}</span>
                                <span className="text-gray-600 ml-1">{user.reputation.toLocaleString()}</span>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>

            <div className="bg-white p-4 rounded-md shadow-sm border border-gray-200">
                <h3 className="font-medium mb-2">Rules and Guidelines</h3>
                <ul className="text-sm space-y-2 text-gray-700">
                    <li>Be respectful and helpful to others</li>
                    <li>Provide context when asking questions</li>
                    <li>Use appropriate tags for your posts</li>
                    <li>Search before asking a new question</li>
                </ul>
            </div>

            <div className="bg-white p-4 rounded-md shadow-sm border border-gray-200">
                <h3 className="font-medium mb-2">Community Stats</h3>
                <div className="text-sm space-y-1 text-gray-700">
                    <div>Users: 12,345</div>
                    <div>Questions: 45,678</div>
                    <div>Answers: 98,765</div>
                </div>
            </div>
        </div>
    )
}

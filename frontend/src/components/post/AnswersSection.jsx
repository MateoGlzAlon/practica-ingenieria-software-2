
import { Award } from "lucide-react"
import { mockData } from "@/app/mockData"
import { ArrowDown, ArrowUp, MessageSquare } from "lucide-react"
import { useState, useEffect } from "react"
import getCommentsOfAPost from "@/api/getCommentsOfAPost"

import createCommentVote from "@/api/comment/createCommentVote"
import getIsCommentVoted from "@/api/comment/getIsCommentVoted"
import setClosedComment from "@/api/comment/setClosedComment"

export default function AnswersSection({ acceptedAnswer, setAcceptedAnswer, idPost, refreshTrigger, userId, setTotalComments }) {

  const [commentsData, setCommentsData] = useState(null)
  const [votedComments, setVotedComments] = useState({})
  const [commentVotes, setCommentVotes] = useState({})

  const fetchComments = async () => {
    try {
      const comments = await getCommentsOfAPost(idPost);
      setCommentsData(comments);
      const accepted = comments.find(c => c.accepted);

      setTotalComments(comments.length);

      const acceptedIds = comments
        .filter(c => c.accepted)
        .map(c => c.id);

      setAcceptedAnswer(acceptedIds);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };

  useEffect(() => {
    if (!idPost) return
    fetchComments()
  }, [idPost, refreshTrigger])


  if (!commentsData) {
    return <p className="text-center py-10">Loading comments...</p>
  }


  const handleCommentVote = async (commentId) => {

    try {
      await createCommentVote({ userId, commentId })
      /*const isVoted = await getIsCommentVoted({ userId, commentId })

      setVotedComments(prev => ({
          ...prev,
          [commentId]: isVoted
      }))

      setCommentVotes(prev => ({
          ...prev,
          [commentId]: prev[commentId] + (isVoted ? 1 : -1)
      }))*/
      await fetchComments();
    } catch (error) {
      console.error("Error voting comment:", error)
    }
  }

  const handleAcceptComment = async (commentId) => {
    try {
      await setClosedComment({ postId: idPost, userId, commentId });

      await fetchComments();
    } catch (error) {
      console.error("Failed to accept/unaccept comment:", error);
    }
  };


  return (
    <div className="space-y-6">
      {commentsData.map((answer, index) => (
        <div
          key={answer.id}
          className={`
            bg-white p-6 rounded-md transition
            ${
              (acceptedAnswer === answer.id ||
              (Array.isArray(acceptedAnswer) && acceptedAnswer.includes(answer.id)))
              ? "border-2 border-green-500 ring-1 ring-green-500"
              : "border border-gray-200"
            }
          `}
        >
          <div className="flex gap-4">
            <div className="flex flex-col items-center">
              <button
                onClick={() => handleCommentVote(answer.id)}
                className="text-gray-400 hover:text-orange-500 transition"
                aria-label="Upvote"
              >
                <ArrowUp
                  size={32}
                  className={`${votedComments[answer.id] ? "text-green-600" : ""} hover:text-pink-500`}
                />
              </button>
              <span className="text-xl font-bold my-2 text-gray-700">
                {Number(commentVotes[answer.id] ?? answer.votes) || 0}
              </span>
              <button
                onClick={() => handleAcceptComment(answer.id)}
                className={`
                  mt-4
                  ${
                    (acceptedAnswer === answer.id ||
                    (Array.isArray(acceptedAnswer) && acceptedAnswer.includes(answer.id)))
                    ? "text-green-500"
                    : "text-gray-400 hover:text-green-500"
                  }
                  transition
                `}
                aria-label="Accept answer"
              >
                <Award size={18} />
              </button>
            </div>

            <div className="flex-1">
              <div className="prose max-w-none">
                <p className="text-gray-700 leading-relaxed mb-4">{answer.content}</p>

                {answer.code && (
                  <div className="bg-gray-50 rounded-md border border-gray-200 mb-6">
                    <div className="bg-gray-100 px-4 py-2 border-b border-gray-200 text-sm font-medium text-gray-700">
                      Solution
                    </div>
                    <pre className="p-4 overflow-x-auto text-sm">
                      <code className="language-javascript">{answer.code}</code>
                    </pre>
                  </div>
                )}

                <div className="flex justify-between items-center pt-4 border-t border-gray-200">
                  <div className="flex space-x-4" />
                  {/* i removed this part*/}

                  <div className="flex items-center bg-blue-50 p-2 rounded-md">
                    <img
                      src={answer.authorProfilePicture || "https://placehold.co/600x400?text=Error"}
                      alt="User avatar"
                      className="w-10 h-10 rounded-full mr-2 border-[0.5px] border-gray-600 object-contain"
                    />
                    <div className="text-sm">
                      <div className="font-medium text-blue-600">{answer.author || "N/A"}</div>
                      <div className="text-gray-500">
                        Answered {new Date(answer.createdAt).toLocaleDateString("en-GB")}
                      </div>
                    </div>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
      ))}
    </div>
  )
}

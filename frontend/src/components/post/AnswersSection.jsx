import { Award, ArrowDown, ArrowUp, MessageSquare } from "lucide-react"
import { useState, useEffect, useContext } from "react"
import getCommentsOfAPost from "@/api/getCommentsOfAPost"
import createCommentVote from "@/api/comment/createCommentVote"
import getIsCommentVoted from "@/api/comment/getIsCommentVoted"
import setClosedComment from "@/api/comment/setClosedComment"
import getUserIdFromLocalStorage from "@/hooks/getUserIdAuth"
import { DATA } from "@/app/data"
import { toast } from 'sonner'
import { useLoggedIn } from "@/hooks/loggedInContext"


export default function AnswersSection({ acceptedAnswer, setAcceptedAnswer, idPost, refreshTrigger, userId, setTotalComments, sortOrder, authorId }) {

  const [commentsData, setCommentsData] = useState(null)
  const [expandedComments, setExpandedComments] = useState({})
  const [votedComments, setVotedComments] = useState({})
  const [commentVotes, setCommentVotes] = useState({})
  const [tipAmounts, setTipAmounts] = useState({})
  const [userIdLS] = useState(getUserIdFromLocalStorage())
  const { loggedIn, setLoggedIn } = useLoggedIn()

  console.log("authorIdPar", authorId)
  console.log("userIDLS", userIdLS)


  const fetchComments = async () => {
    try {
      const comments = await getCommentsOfAPost(idPost, sortOrder);
      setCommentsData(comments);
      const accepted = comments.find(c => c.accepted);

      setTotalComments(comments.length);

      const acceptedIds = comments
        .filter(c => c.accepted)
        .map(c => c.id);

      setAcceptedAnswer(acceptedIds);

      const votedMap = {}
      await Promise.all(
        comments.map(async c => {
          votedMap[c.id] = await getIsCommentVoted({
            userId: userIdLS,
            commentId: c.id
          })
        })
      )
      setVotedComments(votedMap)
    } catch (error) {
      console.error('Error fetching comments:', error)
    }
  }

  useEffect(() => {
    if (!idPost) return;

    fetchComments();

  }, [idPost, refreshTrigger, sortOrder]);

  if (!commentsData) {
    return <p className="text-center py-10">Loading comments...</p>
  }

  const handleCommentVote = async (commentId) => {

    if (!userIdLS) return;

    try {
      await createCommentVote({ userId: userIdLS, commentId })
      const isVoted = await getIsCommentVoted({ userId: userIdLS, commentId })

      setVotedComments(prev => ({ ...prev, [commentId]: isVoted }))

      await fetchComments();
    } catch (error) {
      console.error("Error voting comment:", error)
    }
  }



  const handleSendTip = async (receiverId, amount) => {

    console.log("LOgged in", loggedIn);
    console.log("useLogIN", useLoggedIn)


    if (!userIdLS) return;
    if (!receiverId || !amount) {
      toast.error("Missing receiver or amount");
      return;
    }


    console.log("SenderId", userIdLS);
    console.log("ReceiverId", receiverId);
    console.log("Amount", amount);

    try {
      const response = await fetch(`${DATA.apiURL}/tips/send`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          senderId: userIdLS,
          receiverId,
          amount: parseInt(amount),
        }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText);
      }

      toast.success(`✅ Payment of ${amount} sent successfully!`);
      setTipAmounts(prev => ({ ...prev, [receiverId]: "" }));
    } catch (error) {
      console.error("Error sending tip:", error);
      toast.error("❌ Tip failed: " + error.message);
    }
  }




  const handleAcceptComment = async (commentId) => {

    if (!userIdLS) return;

    try {
      await setClosedComment({
        postId: idPost,
        userId: userIdLS,
        commentId
      });

      await fetchComments();
    } catch (error) {
      console.error("Failed to accept comment:", error)
    }
  }

  return (
    <div className="space-y-6">
      {commentsData.map((answer) => (
        <div
          key={answer.id}
          className={`
            bg-white p-6 rounded-md transition
            ${(acceptedAnswer === answer.id ||
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
                className={`
                  ${votedComments[answer.id]
                    ? "text-orange-500"
                    : "text-gray-400 hover:text-orange-500"}
                  transition
                `}
                aria-label="Upvote"
              >
                <ArrowUp
                  size={32}
                  className={`${votedComments[answer.id] ? "text-orange-600" : ""} hover:text-pink-500`}
                />
              </button>
              <span className="text-xl font-bold my-2 text-gray-700">
                {Number(commentVotes[answer.id] ?? answer.votes) || 0}
              </span>

              {loggedIn == true && userIdLS == authorId ?
                <button
                  onClick={() => handleAcceptComment(answer.id)}
                  className={`
                  mt-4
                  ${(acceptedAnswer === answer.id ||
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
                :
                <></>
              }

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

                <div className="flex justify-between items-center pt-4 border-t border-gray-200 mt-6">
                  {console.log("loggin", loggedIn, userIdLS, answer.authorId, userIdLS !== answer.authorId)}

                  {loggedIn == true ? <>
                    {(userIdLS != answer.authorId) == true ?
                      <div className="mt-4 flex gap-2">
                        <input
                          type="number"
                          placeholder="Amount"
                          value={tipAmounts[answer.id] || ""}
                          onChange={(e) =>
                            setTipAmounts({ ...tipAmounts, [answer.id]: e.target.value })
                          }
                          className="border px-2 py-1 rounded text-sm w-24"
                        />
                        <button
                          className="bg-orange-500 text-white px-3 py-1 rounded text-sm"
                          onClick={() => handleSendTip(answer.authorId, tipAmounts[answer.id])}
                        >
                          Send tip
                        </button>
                      </div>
                      :
                      <div></div>
                    }
                  </>
                    :
                    <div className="mt-4 flex items-center gap-2 border border-red-300 bg-red-50 text-red-700 px-3 py-1 rounded text-sm font-medium">
                      Log in to send tips
                    </div>
                  }






                  <div className="flex items-center bg-blue-50 p-2 rounded-md">
                    <img
                      src={answer.authorProfilePicture || "https://placehold.co/600x400?text=User"}
                      alt="User avatar"
                      className="w-10 h-10 rounded-full mr-2 border border-gray-400 object-cover"
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

local mike_dialogue = {
	start_convo = {
		text = "Hey kid.",
		options = {
			{ text = "Hello?", nextNode = "said_Hello" },
			{ text = "Who are you?", nextNode = "asked_whoAreYou" }
		}
	},
	said_Hello = {
		text = "Mike's the name kid, you already forgot?",
		options = {
			{ text = "No I was just- wait do I know you?", nextNode = "end_convo" }
		}
	},
	asked_whoAreYou = {
		text = "I'm Mike. Don't you remember?",
		options = {
			{ text = "Sorry, I don't recall.", nextNode = "end_convo" }
		}
	},
	end_convo = {
		text = "Well, try to remember next time, kid.",
		options = {}
	}
}

return mike_dialogue

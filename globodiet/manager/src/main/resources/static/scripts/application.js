function highlight_dependants() {
	var spans = Array.from(document.getElementsByClassName('card-title'));
	spans.forEach(span => {
	   var highlighted = span.innerHTML;
	   highlighted = highlighted.replace("Dependent ", "Dependent <b>")
	   highlighted = highlighted.replace(" Mapped By ", "</b> mapped by <b>") + "</b>" 
	   span.innerHTML = highlighted;
	});
}

$(document).ready(
	highlight_dependants
);

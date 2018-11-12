/**
 * 
 * Bulma
 */
(function() {
    var burger = document.querySelector('.burger');
    var menu = document.querySelector('#'+burger.dataset.target);
    burger.addEventListener('click', function() {
        burger.classList.toggle('is-active');
        menu.classList.toggle('is-active');
    });
    
    window.onload = onLoadActions;
})();

/**
 * On Load Actions
 * 
 */
function onLoadActions() {
	toggleElemBy('id', 'products-tabs', true);
}


/**
 * Show/Hide and element by id, class or CSS Selector
 * @param by
 * @param val
 * @param hide
 * 
 */
function toggleElemBy(by, val, hide) {
	let elem = null;
	if (by == 'id') {
		elem = document.getElementById(val);
	} else if (by == 'class') {
		if (document.getElementsByClassName(val).length > 0)
		{
			elem = document.getElementsByClassName(val)[0];
		}
	} else if (by == 'css') {
		elem = document.querySelector(val);
	}

	if (elem != null) {
		if (hide == true) {
			elem.style.display = "none";
		} else {
			elem.style.display = "block";
		}
	}
}

/**
 * 
 * Navbar Toggle
 */
document.addEventListener('DOMContentLoaded', () => {
    // Get all "navbar-burger" elements
    const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);
  
    // Check if there are any navbar burgers
    if ($navbarBurgers.length > 0) {
  
      // Add a click event on each of them
      $navbarBurgers.forEach( el => {
        el.addEventListener('click', () => {
  
          // Get the target from the "data-target" attribute
          const target = el.dataset.target;
          const $target = document.getElementById(target);

          // Toggle the "is-active" class on both the "navbar-burger" and the
			// "navbar-menu"
          el.classList.toggle('is-active');
          $target.classList.toggle('is-active');
  
        });
      });
    }  
});

/**
 * 
 * Tabs Toggle
 */
document.querySelectorAll("#tabNav li").forEach(function(navEl) {
	navEl.onclick = function() { toggleTab(this.id, this.dataset.target); }
//	toggleTabContent(navEl.dataset.target);
	if (navEl.className.includes("is-active")) {
		toggleTab(navEl.id, navEl.dataset.target);
	}
});

function toggleTab(selectedNav, targetId) {
  var navEls = document.querySelectorAll("#tabNav li");
  navEls.forEach(function(navEl) {
    if (navEl.id == selectedNav) {
      navEl.classList.add("is-active");
    } else {
      if (navEl.classList.contains("is-active")) {
        navEl.classList.remove("is-active");
      }
    }
  });

  toggleTabContent(targetId);  
}

function toggleTabContent(targetId) {
	var tabs = document.querySelectorAll(".tab-pane");

	tabs.forEach(function(tab) {
		if (tab.id == targetId) {
			tab.style.display = "block";
		} else {
			tab.style.display = "none";
		}
	});
}




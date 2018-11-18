var appURL = location.href;
var appPath = location.pathname;
var appRootPath = "/" + appPath.split("/")[1] + "/";


/**
 * 
 * Bulma
 */
(function() {
    let burger = document.querySelector('.burger');
    let menu = document.querySelector('#'+burger.dataset.target);
    burger.addEventListener('click', function() {
        burger.classList.toggle('is-active');
        menu.classList.toggle('is-active');
    });

    onClickViewCatItem();

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
  let navEls = document.querySelectorAll("#tabNav li");
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
	let tabs = document.querySelectorAll(".tab-pane");

	tabs.forEach(function(tab) {
		if (tab.id == targetId) {
			tab.style.display = "block";
		} else {
			tab.style.display = "none";
		}
	});
}

function hideAllCatsViewAllLink() {
	let viewCatItemsBtns = document.querySelectorAll('a[id^="view-cat-item-"]');
	viewCatItemsBtns.forEach(function (el) {
		toggleElemBy('id', el.id, true);
	});
}

function onClickViewCatItem() {
	let viewCatItemsBtns = document.querySelectorAll('a[id^="view-cat-item-"]');

	viewCatItemsBtns.forEach(function (el) {
		el.addEventListener('click', () => {
			
			toggleElemBy('id', 'page-products', true);
			toggleElemBy('id', 'products-tabs', false);
			hideAllCatsViewAllLink();
			
			let catId = el.id.split("view-cat-item-")[1];
			document.getElementById("cat-" + catId).click();
			
		});
	});
}


function onClickAddToCartBtn(el) {
	let productId = el.id.split("product-")[1];
	let addToCartUrl = appRootPath + 'Api/Cart';

	let qs = "addToCart=" + productId;
	doSimpleAjax(addToCartUrl, qs, addToCartResult);
}

function addToCartResult(request)
{
	// TODO: increase num on top cart btn
	console.log('addeded to cart', request.responseText);
}


function updateQty(el) {
	console.log("updating item qty");
	let cartUrl = appRootPath + 'Api/Cart';
	let productNum = el.parentNode.parentNode.id.split('p-')[1];
	let productQty = document.getElementById("qty-" + productNum).value;
	
	console.log(productNum);

	let qs = "updateQty=" + productNum + "&qty=" + productQty;
	doSimpleAjax(cartUrl, qs, updateQtyResult);
}

function updateQtyResult(request)
{
	// TODO: increase num on top cart btn
	console.log('updated qty in cart', request.responseText);
	updatePage();
}


function deleteCartItem(el) {
	let cartUrl = appRootPath + 'Api/Cart';
	let productNum = el.parentNode.parentNode.id.split('p-')[1];

	let qs = "removeItem=" + productNum;
	doSimpleAjax(cartUrl, qs, deleteCartItemResult);	
}

function deleteCartItemResult(request)
{
	let req = request.responseText;
	updatePage();
	
	if (req != null && req.action == "REMOVE") {
		let el = document.getElementById('p-' + req.actionReq);
		console.log(el);
		console.log(el.parentNode.parentNode);
		let i = el.parentNode.parentNode.rowIndex;
	    document.getElementById("cartItems").deleteRow(i);
	    console.log('deleted from cart', req);
	}
}

function doCheckout() {
	location.href = appRootPath + "Checkout";
}

function updatePage() {
	location.reload(true);
}

function gotoHomePage() {
	location.href = appRootPath;
}


function doSimpleAjax(address, data, handler)
{
    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {handler(request);};
    request.open("GET", (address + "?" + data), true);
    request.send(null);
}




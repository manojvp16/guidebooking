/* =============================================
   GUIDE BOOKING — MAIN JS
   ============================================= */

document.addEventListener('DOMContentLoaded', () => {

  /* ---- Mobile hamburger toggle ---- */
  const hamburger = document.querySelector('.hamburger');
  const navMenu   = document.querySelector('.navbar-nav');
  if (hamburger && navMenu) {
    hamburger.addEventListener('click', () => {
      const isOpen = navMenu.dataset.open === 'true';
      navMenu.dataset.open = !isOpen;
      Object.assign(navMenu.style, isOpen ? {
        display: '', flexDirection: '', position: '',
        top: '', left: '', right: '', background: '', padding: '', zIndex: ''
      } : {
        display: 'flex', flexDirection: 'column', position: 'absolute',
        top: '68px', left: '0', right: '0',
        background: '#083d42', padding: '1rem', gap: '.5rem', zIndex: '999'
      });
    });
  }

  /* ---- Scroll-triggered card animations ---- */
  const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('animate');
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.1 });

  document.querySelectorAll('.guide-card, .stat-card, .review-card').forEach(el => {
    observer.observe(el);
  });

  /* ---- Auto-dismiss alert messages after 4s ---- */
  document.querySelectorAll('.alert').forEach(alert => {
    setTimeout(() => {
      alert.style.transition = 'opacity .4s';
      alert.style.opacity    = '0';
      setTimeout(() => alert.remove(), 400);
    }, 4000);
  });

  /* ---- Confirm before destructive actions ---- */
  document.querySelectorAll('[data-confirm]').forEach(btn => {
    btn.addEventListener('click', e => {
      if (!confirm(btn.dataset.confirm || 'Are you sure?')) e.preventDefault();
    });
  });

  /* ---- Image fallback for broken guide photos ---- */
  document.querySelectorAll('.guide-card-img img').forEach(img => {
    img.addEventListener('error', () => {
      img.style.display = 'none';
      const ph = document.createElement('div');
      ph.style.cssText =
        'width:100%;height:100%;background:linear-gradient(135deg,#0d5c63,#1a7a82);' +
        'display:flex;align-items:center;justify-content:center;font-size:4rem;';
      ph.textContent = '🧭';
      img.parentNode.appendChild(ph);
    });
  });

  /* ---- Render star displays from data-rating attribute ---- */
  document.querySelectorAll('.stars-display').forEach(el => {
    const rating = parseFloat(el.dataset.rating) || 0;
    el.innerHTML = Array.from({ length: 5 }, (_, i) =>
      `<span class="star ${i < Math.round(rating) ? '' : 'empty'}">★</span>`
    ).join('');
  });

  /* ---- Highlight active nav link ---- */
  const currentPath = window.location.pathname;
  document.querySelectorAll('.nav-link').forEach(link => {
    if (link.getAttribute('href') === currentPath) link.classList.add('active');
  });

  /* ---- Interactive star rating on review form ---- */
  const starBtns = document.querySelectorAll('.star-btn');
  if (starBtns.length) {
    const container = starBtns[0].closest('div');

    const paint = (count, color) => {
      starBtns.forEach((s, i) => { s.style.color = i < count ? color : '#d8d0c4'; });
    };

    starBtns.forEach((star, idx) => {
      star.addEventListener('click', () => {
        const radio = star.previousElementSibling;
        if (radio) radio.checked = true;
        paint(idx + 1, '#c9952a');
      });
      star.addEventListener('mouseover', () => paint(idx + 1, '#e8b84b'));
    });

    if (container) {
      container.addEventListener('mouseleave', () => {
        const checked = document.querySelector('input[name="rating"]:checked');
        paint(checked ? parseInt(checked.value) : 0, '#c9952a');
      });
    }
  }

});